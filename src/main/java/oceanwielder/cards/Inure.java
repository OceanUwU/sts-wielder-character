package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import oceanwielder.powers.LambdaPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Inure extends AbstractWielderCard {
    public final static String ID = makeID(Inure.class.getSimpleName());

    public Inure() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void onLoseHPFromWeight(int weight) {
                flash();
                applyToSelfTop(new EnergizedPower(p, amount));
                getTixTop(amount);
            }
            
            @Override public void updateDescription() {
                description = strings[1] + amount + strings[2];
                for (int i = 0; i < amount; i++)
                    description += " [E]";
                description += strings[3];
            }
        });
    }
}