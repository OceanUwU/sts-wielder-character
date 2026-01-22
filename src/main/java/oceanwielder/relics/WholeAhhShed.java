package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;

import oceanwielder.characters.TheWielder;

public class WholeAhhShed extends AbstractWielderRelic {
    public static final String ID = makeID("WholeAhhShed");

    public WholeAhhShed() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
