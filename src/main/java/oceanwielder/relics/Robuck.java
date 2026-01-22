package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;

import oceanwielder.characters.TheWielder;

public class Robuck extends AbstractWielderRelic {
    public static final String ID = makeID("Robuck");

    public Robuck() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
