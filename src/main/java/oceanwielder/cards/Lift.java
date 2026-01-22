package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import oceanwielder.powers.LambdaPower;
import oceanwielder.powers.Weight;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Lift extends AbstractWielderCard {
    public final static String ID = makeID(Lift.class.getSimpleName());

    public Lift() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(6, +2);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void atStartOfTurnPostDraw() {
                flash();
                applyToSelf(new VigorPower(p, amount));
            }
            
            @Override public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
        applyToSelf(new LambdaPower(ID + "Weight", exDesc, exDesc[3], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF, false, p, secondMagic) {
            @Override
            public void atStartOfTurnPostDraw() {
                flash();
                applyToSelf(new Weight(p, amount));
            }
            
            @Override public void updateDescription() {
                description = strings[4] + amount + strings[5];
            }
        });
    }
}