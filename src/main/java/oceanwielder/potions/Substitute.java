package oceanwielder.potions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import oceanwielder.characters.TheWielder;
import oceanwielder.util.TexLoader;
import oceanwielder.wieldables.WieldableLibrary;
import oceanwielder.WielderMod;
import oceanwielder.actions.WieldAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;
import static oceanwielder.util.Wiz.*;

public class Substitute extends AbstractEasyPotion {
    public static String ID = makeID("Substitute");

    public Substitute() {
        super(ID, PotionRarity.RARE, PotionSize.GHOST, new Color(0.0f, 0.0f, 0.0f, 1f), Color.WHITE, null, TheWielder.Enums.OCEAN_WIELDER_CLASS, WielderMod.characterColor);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", TexLoader.getTexture(makeImagePath("potions/substitute.png")));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "outlineImg", TexLoader.getTexture(makeImagePath("potions/substituteoutline.png")));
    }

    public void use(AbstractCreature creature) {
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.weapons, true)));
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.shields, true)));
    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void addAdditionalTips() {
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("wield")), BaseMod.getKeywordDescription(makeID("wield"))));
    }

    @Override
    public int getPotency(int arg0) {
        return 0;
    }
}