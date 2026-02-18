package oceanwielder.potions;

import oceanwielder.characters.TheWielder;
import oceanwielder.WielderMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class BottledRage extends AbstractEasyPotion {
    public static String ID = makeID("BottledRage");

    public BottledRage() {
        super(ID, PotionRarity.COMMON, PotionSize.BOTTLE, new Color(0.92f, 0.25f, 0.20f, 1f), new Color(0.92f, 0.43f, 0.20f, 1f), null, TheWielder.Enums.OCEAN_WIELDER_CLASS, WielderMod.characterColor);
    }

    public int getPotency(int ascensionlevel) {
        return 9;
    }

    public void use(AbstractCreature creature) {
        applyToSelf(new VigorPower(adp(), potency));
    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0] + potency + strings.DESCRIPTIONS[1];
    }

    public void addAdditionalTips() {
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.VIGOR.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.VIGOR.NAMES[0])));
    }
}