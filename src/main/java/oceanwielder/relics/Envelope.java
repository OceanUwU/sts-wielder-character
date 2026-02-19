package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import oceanwielder.characters.TheWielder;

public class Envelope extends AbstractWielderRelic {
    public static final String ID = makeID("Envelope");

    public Envelope() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
