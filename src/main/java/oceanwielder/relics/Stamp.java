package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;

import oceanwielder.characters.TheWielder;

public class Stamp extends AbstractWielderRelic {
    public static final String ID = makeID("Stamp");

    public Stamp() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
