package oceanwielder.characters;

import static oceanwielder.WielderMod.*;
import static oceanwielder.characters.TheWielder.Enums.OCEAN_WIELDER_COLOUR;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import java.util.List;
import oceanwielder.cards.Defend;
import oceanwielder.cards.Guzzle;
import oceanwielder.cards.Strike;
import oceanwielder.cards.Twirl;
import oceanwielder.relics.RemarkablyThrowableRock;
import oceanwielder.util.TexLoader;
import oceanwielder.vfx.WielderVictoryEffect;

public class TheWielder extends CustomPlayer {
    static final String ID = makeID("ModdedCharacter");
    private static final float ORB_IMG_SCALE = 1.15F * Settings.scale;
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;
    public static final Float SIZE_SCALE = 1.0f;
    public static final Float ANIMATION_SPEED = 1.0f;


    public TheWielder(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, makeCharacterPath("mainChar/orb/vfx.png"), new float[] { 40f, 60f, 80f, 40f, 0f }) {
            private FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
            private Texture mask = TexLoader.getTexture(makeCharacterPath("mainChar/orb/mask.png"));

            @Override
            public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
                sb.end();
                fbo.begin();
                Gdx.gl.glClearColor(0, 0, 0, 0);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                Gdx.gl.glColorMask(true, true, true, true);
                sb.begin();
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                for(int i = 0; i < energyLayers.length; ++i)
                    sb.draw((enabled ? energyLayers : noEnergyLayers)[i], current_x - 64f, current_y - 64f, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angles[i], 0, 0, 128, 128, false, false);
                sb.setBlendFunction(0, GL20.GL_SRC_ALPHA);
                sb.setColor(Color.WHITE);
                sb.draw(mask, current_x - 256, current_y - 256, 256, 256, 512, 512, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 512, 512, false, false);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sb.end();
                fbo.end();
                sb.begin();
                TextureRegion drawTex = new TextureRegion(fbo.getColorBufferTexture());
                drawTex.flip(false, true);
                sb.draw(drawTex, -Settings.VERT_LETTERBOX_AMT, -Settings.HORIZ_LETTERBOX_AMT);
                sb.draw(baseLayer, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
            }
        }, new SpineAnimation(
                makeCharacterPath("mainChar/foxts3.atlas"), makeCharacterPath("mainChar/foxts3.json"), SIZE_SCALE));
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(3));

        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        stateData.setMix("Hit", "Idle", 0.2f);
        e.setTimeScale(ANIMATION_SPEED);
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
    }

    @Override
    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - currentBlock > 0) {
            AnimationState.TrackEntry e = state.setAnimation(0, "Hit", false);
            AnimationState.TrackEntry e2 = state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(ANIMATION_SPEED);
            e2.setTimeScale(ANIMATION_SPEED);
        }
        super.damage(info);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                75, 75, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            retVal.add(Strike.ID);
        for (int i = 0; i < 4; i++)
            retVal.add(Defend.ID);
        retVal.add(Twirl.ID);
        retVal.add(Guzzle.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(RemarkablyThrowableRock.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    private static final String[] orbTextures = {
            makeCharacterPath("mainChar/orb/layer1.png"),
            makeCharacterPath("mainChar/orb/layer2.png"),
            makeCharacterPath("mainChar/orb/layer3.png"),
            makeCharacterPath("mainChar/orb/layer4.png"),
            makeCharacterPath("mainChar/orb/layer5.png"),
            makeCharacterPath("mainChar/orb/layer6.png"),
            makeCharacterPath("mainChar/orb/layer1d.png"),
            makeCharacterPath("mainChar/orb/layer2d.png"),
            makeCharacterPath("mainChar/orb/layer3d.png"),
            makeCharacterPath("mainChar/orb/layer4d.png"),
            makeCharacterPath("mainChar/orb/layer5d.png"),
    };

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return OCEAN_WIELDER_COLOUR;
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        System.out.println("YOU NEED TO SET getStartCardForEvent() in your " + getClass().getSimpleName() + " file!");
        return null;
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheWielder(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    private static boolean endEffectStarted = false;
    @Override
    public List<CutscenePanel> getCutscenePanels() {
        endEffectStarted = false;
        return super.getCutscenePanels();
        //List<CutscenePanel> panels = new ArrayList<>();
        //panels.add(new CutscenePanel(makeImagePath("ending/1.png"), "RELIC_DROP_CLINK"));
        //panels.add(new CutscenePanel(makeImagePath("ending/2.png")));
        //panels.add(new CutscenePanel(makeImagePath("ending/3.png")));
        //return panels;
    }

    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        if (!endEffectStarted) {
            effects.add(new WielderVictoryEffect());
            endEffectStarted = true;
        }
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass OCEAN_WIELDER_CLASS;
        @SpireEnum(name = "OCEAN_WIELDER_COLOUR")
        public static AbstractCard.CardColor OCEAN_WIELDER_COLOUR;
        @SpireEnum(name = "OCEAN_WIELDER_COLOUR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
