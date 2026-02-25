package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.GuardAction;
import oceanwielder.powers.LambdaPower;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class SealOfApproval extends AbstractWielderCard {
    public final static String ID = makeID(SealOfApproval.class.getSimpleName());

    public SealOfApproval() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void onUseCard(AbstractCard card, UseCardAction action) {
                int stamps = Stamps.getStamps(card);
                if (stamps > 0) {
                    flash();
                    atb(new GuardAction(amount, null, true));
                }
            }
            
            @Override public void updateDescription() {
                description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
            }
        });
        if (upgraded)
            actB(() -> adp().hand.group.forEach(c -> {
                att(new Stamps.Action(c));
            }));
    }
}