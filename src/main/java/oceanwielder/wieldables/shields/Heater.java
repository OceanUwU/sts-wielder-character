package oceanwielder.wieldables.shields;

import com.megacrit.cardcrawl.powers.DexterityPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Heater extends AbstractShield {
    public static String ID = makeID("Heater");

    public Heater() {
        super(ID, 2, 1);
        primaryTimes = 2;
    }

    public void dequipEffect() {
        applyToSelfTop(new DexterityPower(adp(), dequipPower));
    }
}
