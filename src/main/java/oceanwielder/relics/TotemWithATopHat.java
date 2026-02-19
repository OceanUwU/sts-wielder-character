package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;

import basemod.BaseMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.helpers.PowerTip;
import oceanwielder.characters.TheWielder;

public class TotemWithATopHat extends AbstractWielderRelic {
    public static final String ID = makeID("TotemWithATopHat");

    public TotemWithATopHat() {
        super(ID, RelicTier.SHOP, LandingSound.SOLID, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }

    @Override
    protected void initializeTips() {
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("tix")), BaseMod.getKeywordDescription(makeID("tix"))));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("stamp")), BaseMod.getKeywordDescription(makeID("stamp"))));
    }
}
