package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanwielder.powers.LambdaPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class EternalDance extends AbstractWielderCard {
    public final static String ID = makeID(EternalDance.class.getSimpleName());

    public EternalDance() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(5, +2);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void onLoseHPFromWeight(int weight) {
                att(new HealAction(p, p, amount), new RemoveSpecificPowerAction(p, p, this));
            }

            @Override
            public boolean shouldCancelWeightHPLoss(int weight) {
                return true;
            }

            @Override
            public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
        bearWeight();
    }
}