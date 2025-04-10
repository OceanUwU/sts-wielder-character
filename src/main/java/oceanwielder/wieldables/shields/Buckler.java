package oceanwielder.wieldables.shields;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Buckler extends AbstractShield {
    public static String ID = makeID("Buckler");

    public Buckler() {
        super(ID, 5, 7);
    }

    public void dequipEffect() {
        att(new GainBlockAction(adp(), dequipPower, true));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        description += strings.DESCRIPTION[0] + dequipPower + strings.DESCRIPTION[1];
    }
}
