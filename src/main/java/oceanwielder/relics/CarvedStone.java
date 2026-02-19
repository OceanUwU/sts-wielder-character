package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import oceanwielder.characters.TheWielder;

public class CarvedStone extends AbstractWielderRelic {
    public static final String ID = makeID("CarvedStone");

    public CarvedStone() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
}
