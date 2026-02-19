package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;

import oceanwielder.characters.TheWielder;
import oceanwielder.util.Stamps;

public class Envelope extends AbstractWielderRelic {
    public static final String ID = makeID("Envelope");
    public static final int POWER = 1;

    public Envelope() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + (Stamps.MoxiePower.MOXIE_PER_ENERGY - POWER) + DESCRIPTIONS[1] + Stamps.MoxiePower.MOXIE_PER_ENERGY + DESCRIPTIONS[2];
    }
}
