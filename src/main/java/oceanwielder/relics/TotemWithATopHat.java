package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import oceanwielder.characters.TheWielder;

public class TotemWithATopHat extends AbstractWielderRelic {
    public static final String ID = makeID("TotemWithATopHat");

    public TotemWithATopHat() {
        super(ID, RelicTier.SHOP, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
