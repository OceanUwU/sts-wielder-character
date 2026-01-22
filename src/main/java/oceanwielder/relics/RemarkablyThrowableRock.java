package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;

import oceanwielder.characters.TheWielder;

public class RemarkablyThrowableRock extends AbstractWielderRelic {
    public static final String ID = makeID("RemarkablyThrowableRock");

    public RemarkablyThrowableRock() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
