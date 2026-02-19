package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.powers.LambdaPower;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class SealOfApproval extends AbstractWielderCard {
    public final static String ID = makeID(SealOfApproval.class.getSimpleName());

    public SealOfApproval() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(4, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void onPlayCard(AbstractCard card, AbstractMonster m) {
                int stamps = Stamps.getStamps(card);
                if (stamps > 0) {
                    flash();
                    for (int i = 0; i < stamps; i++)
                        atb(new GainBlockAction(owner, owner, amount, true));
                }
            }
            
            @Override public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
    }
}