package oceanwielder.potions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import oceanwielder.characters.TheWielder;
import oceanwielder.util.TexLoader;
import oceanwielder.WielderMod;
import oceanwielder.actions.GainTixAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;
import static oceanwielder.util.Wiz.*;

public class Ticket extends AbstractEasyPotion {
    public static String ID = makeID("Ticket");

    public Ticket() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.GHOST, new Color(0.0f, 0.0f, 0.0f, 1f), Color.WHITE, null, TheWielder.Enums.OCEAN_WIELDER_CLASS, WielderMod.characterColor);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", TexLoader.getTexture(makeImagePath("potions/tix.png")));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "outlineImg", TexLoader.getTexture(makeImagePath("potions/tixoutline.png")));
    }

    public int getPotency(int ascensionlevel) {
        return 3;
    }

    public void use(AbstractCreature creature) {
        atb(new GainTixAction(potency));
    }

    public String getDescription() {
        String str = strings.DESCRIPTIONS[0];
        for (int i = 0; i < potency; i++)
            str += strings.DESCRIPTIONS[1];
        return str + strings.DESCRIPTIONS[2];
    }

    public void addAdditionalTips() {
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("tix")), BaseMod.getKeywordDescription(makeID("tix"))));
    }
}