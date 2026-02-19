package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import oceanwielder.characters.TheWielder;

public class SharpRuby extends AbstractWielderRelic {
    public static final String ID = makeID("SharpRuby");

    public SharpRuby() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
