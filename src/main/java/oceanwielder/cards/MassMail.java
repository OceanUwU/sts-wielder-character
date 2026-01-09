package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.powers.LambdaPower;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class MassMail extends AbstractWielderCard {
    public final static String ID = makeID(MassMail.class.getSimpleName());

    public MassMail() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        setMagic(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            public void onUseCard(AbstractCard card, UseCardAction action) {
                if (!card.purgeOnUse && Stamps.isStamped(card) && amount > 0) {
                    flash();
                    AbstractMonster m = null;
                    if (action.target != null) {
                        m = (AbstractMonster)action.target;
                    }

                    AbstractCard tmp = card.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = card.current_x;
                    tmp.current_y = card.current_y;
                    tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                    tmp.target_y = (float)Settings.HEIGHT / 2.0F;
                    if (m != null) {
                        tmp.calculateCardDamage(m);
                    }

                    tmp.purgeOnUse = true;
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
                    --amount;
                    if (amount == 0) {
                        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
                    }
                }

            }

            public void atEndOfTurn(boolean isPlayer) {
                if (isPlayer)
                    addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            }
            
            @Override public void updateDescription() {
                description = amount == 1 ? strings[1] : (strings[2] + amount + strings[3]);
            }
        });
    }
}