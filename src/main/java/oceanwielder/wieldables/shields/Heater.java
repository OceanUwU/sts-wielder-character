package oceanwielder.wieldables.shields;

import oceanwielder.powers.AegisPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Heater extends AbstractShield {
    public static String ID = makeID("Heater");

    public Heater() {
        super(ID, 2, 6);
        basePrimaryTimes = primaryTimes = 2;
    }

    public void dequipEffect() {
        applyToSelfTop(new AegisPower(adp(), dequipPower));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        description += strings.DESCRIPTION[0] + dequipPower + strings.DESCRIPTION[1];
    }
}
