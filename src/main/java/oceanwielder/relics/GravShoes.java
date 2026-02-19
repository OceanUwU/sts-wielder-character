package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;

import oceanwielder.characters.TheWielder;

public class GravShoes extends AbstractWielderRelic {
    public static final String ID = makeID("GravShoes");
    public static int POWER = 2;

    public GravShoes() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }
}
