package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;

import oceanwielder.characters.TheWielder;

public class PunchCard extends AbstractWielderRelic {
    public static final String ID = makeID("PunchCard");

    public PunchCard() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
