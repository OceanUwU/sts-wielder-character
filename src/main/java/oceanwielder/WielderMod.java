package oceanwielder;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.nio.charset.StandardCharsets;
import oceanwielder.cards.AbstractWielderCard;
import oceanwielder.cards.cardvars.*;
import oceanwielder.characters.TheWielder;
import oceanwielder.relics.AbstractWielderRelic;
import oceanwielder.util.WielderAudio;
import oceanwielder.wieldables.AbstractWieldable;
import oceanwielder.wieldables.WieldableLibrary;
import oceanwielder.wieldables.WieldableSlot;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class WielderMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber {

    public static final String modID = "oceanwielder";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(0.25f, 0.09f, 0.69f, 1); // This should be changed eventually

    public static final String SHOULDER1 = makeCharacterPath("mainChar/shoulder.png");
    public static final String SHOULDER2 = makeCharacterPath("mainChar/shoulder2.png");
    public static final String CORPSE = makeCharacterPath("mainChar/corpse.png");
    private static final String ATTACK_S_ART = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART = makeImagePath("512/skill.png");
    private static final String POWER_S_ART = makeImagePath("512/power.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String TEXT_ENERGY = makeImagePath("512/text_energy.png");
    private static final String ATTACK_L_ART = makeImagePath("1024/attack.png");
    private static final String SKILL_L_ART = makeImagePath("1024/skill.png");
    private static final String POWER_L_ART = makeImagePath("1024/power.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");
    private static final String CHARSELECT_BUTTON = makeImagePath("charSelect/charButton.png");
    private static final String CHARSELECT_PORTRAIT = makeImagePath("charSelect/charBG.png");

    public static WieldableSlot weaponSlot;
    public static WieldableSlot shieldSlot;

    @SpireEnum public static AbstractCard.CardTags Stab;
    @SpireEnum public static AbstractCard.CardTags Jab;

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    public WielderMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheWielder.Enums.OCEAN_WIELDER_COLOUR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCharacterPath(String resourcePath)
    {
        return modID + "Resources/images/char/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        WielderMod thismod = new WielderMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheWielder(TheWielder.characterStrings.NAMES[1], TheWielder.Enums.OCEAN_WIELDER_CLASS),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheWielder.Enums.OCEAN_WIELDER_CLASS);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractWielderRelic.class)
                .any(AbstractWielderRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new Hits());
        BaseMod.addDynamicVariable(new Guards());
        new AutoAdd(modID)
                .packageFilter(AbstractWielderCard.class)
                .setDefaultSeen(true)
                .cards();
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
        //BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + getLangString() + "/Potionstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, modID + "Resources/localization/" + getLangString() + "/Orbstrings.json");
        //BaseMod.loadCustomStringsFile(StanceStrings.class, modID + "Resources/localization/" + getLangString() + "/Stancestrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        WieldableLibrary.initialize();

        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/" + getLangString() + "/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                if (keyword.DESCRIPTION.contains("!P!")) {
                    AbstractWieldable wieldable = WieldableLibrary.get(makeID(keyword.PROPER_NAME.replaceAll(" ", "_")));
                    keyword.DESCRIPTION = keyword.DESCRIPTION
                        .replace("!P!", Integer.toString(wieldable.primary) + (wieldable.primaryTimes == 1 ? "" : "x"+wieldable.primaryTimes))
                        .replace("!S!", Integer.toString(wieldable.secondary) + (wieldable.secondaryTimes == 1 ? "" : "x"+wieldable.secondaryTimes))
                        .replace("!D!", Integer.toString(wieldable.dequipPower) + (wieldable.dequipTimes == 1 ? "" : "x"+wieldable.dequipTimes));
                }
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveAddAudio() {
        for (WielderAudio a : WielderAudio.values())
            BaseMod.addAudio(makeID(a.name()), makePath("audio/" + a.name().toLowerCase() + ".ogg"));
    }

    @Override
    public void receivePostInitialize() {
        weaponSlot = new WieldableSlot(WieldableLibrary.defaultWeapon);
        shieldSlot = new WieldableSlot(WieldableLibrary.defaultShield);
    }
}
