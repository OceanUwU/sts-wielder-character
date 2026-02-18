package oceanwielder.potions;

import basemod.BaseMod;
import oceanwielder.characters.TheWielder;
import oceanwielder.powers.AegisPower;
import oceanwielder.WielderMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class HolyWater extends AbstractEasyPotion {
    public static String ID = makeID("HolyWater");

    public HolyWater() {
        super(ID, PotionRarity.COMMON, PotionSize.BOTTLE, new Color(0.2f, 0.4f, 0.9f, 1f), new Color(0.6f, 0.8f, 1.0f, 1f), null, TheWielder.Enums.OCEAN_WIELDER_CLASS, WielderMod.characterColor);
    }

    public int getPotency(int ascensionlevel) {
        return 8;
    }

    public void use(AbstractCreature creature) {
        applyToSelf(new AegisPower(adp(), potency));
    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0] + potency + strings.DESCRIPTIONS[1];
    }

    public void addAdditionalTips() {
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("aegis")), BaseMod.getKeywordDescription(makeID("aegis"))));
    }
}