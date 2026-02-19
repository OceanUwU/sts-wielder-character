package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import oceanwielder.characters.TheWielder;

public class Okebaka extends AbstractWielderRelic {
    public static final String ID = makeID("Okebaka");

    public Okebaka() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
