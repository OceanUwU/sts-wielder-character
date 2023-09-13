package oceanwielder.wieldables.shields;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Buckler extends AbstractShield {
    private static String ID = makeID("Buckler");

    public Buckler() {
        super(ID, 5, -1, 7);
    }

    public void dequipEffect() {
        att(new GainBlockAction(adp(), dequipPower, true));
    }
}
