package oceanwielder.wieldables.shields;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Kite extends AbstractShield {
    public static String ID = makeID("Kite");

    public Kite() {
        super(ID, 6, 2, 1);
    }

    @Override
    public void use(AbstractMonster m) {
        applyToSelfTop(new VigorPower(adp(), secondary));
        super.use(m);
    }

    public void dequipEffect() {
        applyToSelfTop(new StrengthPower(adp(), dequipPower));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        description += strings.DESCRIPTION[0] + secondary + strings.DESCRIPTION[1] + dequipPower + strings.DESCRIPTION[2];
    }
}