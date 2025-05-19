package oceanwielder.util;

import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import java.lang.reflect.Field;
import java.util.ArrayList;
import oceanwielder.powers.AbstractWielderPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;
import static oceanwielder.util.Wiz.*;

public class Stamps {
    public static class StampedMod extends AbstractCardModifier {
        public static final String ID = makeID("StampedMod");
        private static final Texture ICON_IMG = TexLoader.getTexture(makeImagePath("512/stamp.png"));
        public int amount;
        public float timer1, timer2, timer3;
        public boolean supplyingKeyword;

        public StampedMod(int amount) {
            this.amount = amount;
        }

        public void onUpdate(AbstractCard c) {
            timer1 += Gdx.graphics.getDeltaTime();
            timer2 += Gdx.graphics.getDeltaTime();
            timer3 += Gdx.graphics.getDeltaTime();
        }

        public void onRender(AbstractCard c, SpriteBatch sb) {
            ExtraIcons.icon(ICON_IMG).text(String.valueOf(amount)).drawColor(new Color(1f, 1f, 1f, c.transparency)).render(c);
        }

        public void onUse(AbstractCard c, AbstractCreature target, UseCardAction action) {
            applyToSelf(new MoxiePower(adp(), StampedMod.this.amount));
            actB(() -> {
                int energy = pwrAmt(adp(), MoxiePower.POWER_ID) / MoxiePower.MOXIE_PER_ENERGY;
                if (energy > 0) {
                    att(new GainEnergyAction(energy));
                    att(new ReducePowerAction(adp(), adp(), MoxiePower.POWER_ID, energy * MoxiePower.MOXIE_PER_ENERGY));
                }
            });
        }

        public boolean shouldApply(AbstractCard c) {
            return !CardModifierManager.hasModifier(c, ID);
        }

        public void onInitialApplication(AbstractCard c) {
            c.initializeDescription();
        }
     
        public void onRemove(AbstractCard c) {
            c.initializeDescription();
        }

        public String identifier(AbstractCard c) {return ID;}
        public StampedMod makeCopy() {return new StampedMod(amount);}
    }
    
    public static class MoxiePower extends AbstractWielderPower {
        public static String POWER_ID = makeID("MoxiePower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        public static int MOXIE_PER_ENERGY = 5;

        public MoxiePower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + MOXIE_PER_ENERGY + powerStrings.DESCRIPTIONS[2];
        }
    }

    public static class Action extends AbstractGameAction {
        AbstractCard c;

        public Action(AbstractCard c, int amount) {
            this.c = c;
            this.amount = amount;
        }

        public Action(AbstractCard c) {
            this(c, 1);
        }

        public void update() {
            if (c != null)
                stamp(c, amount);
            isDone = true;
        }
    }

    public static class GroupAction extends AbstractGameAction {
        CardGroup g;

        public GroupAction(CardGroup g, int amount) {
            this.g = g;
            this.amount = amount;
        }

        public GroupAction(CardGroup g) {
            this(g, 1);
        }

        public void update() {
            for (AbstractCard c : g.group)
                att(new Action(c, amount));
            isDone = true;
        }
    }

    public static class RandomAction extends AbstractGameAction {
        public RandomAction(int amount) {
            this.amount = amount;
        }

        public RandomAction() {
            this(1);
        }

        public void update() {
            isDone = true;
            CardGroup stampable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : adp().hand.group)
                stampable.addToTop(c);
            if (stampable.size() > 0) {
                stampable.shuffle();
                int toStamp = amount;
                for (AbstractCard c : stampable.group) {
                    att(new Action(c));
                    if (--toStamp <= 0)
                        break;
                }
            }
        }
    }

    public static void stamp(AbstractCard c, int amount) {
        StampedMod modifier = getModifier(c);
        if (modifier == null)
            CardModifierManager.addModifier(c, new StampedMod(amount));
        else {
            if (modifier.amount < 2)
                modifier.timer2 = 0f;
            if (modifier.amount < 3)
                modifier.timer3 = 0f;
            modifier.amount += amount;
        }
        c.flash(Color.RED.cpy());
    }

    public static void stamp(AbstractCard c) {
        stamp(c, 1);
    }

    public static boolean canStamp(AbstractCard c) {
        return c.cost > -2;
    }

    public static StampedMod getModifier(AbstractCard c) {
        ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(c, StampedMod.ID);
        if (modifiers.size() > 0)
            return (StampedMod)modifiers.get(0);
        return null;
    }

    public static int getStamps(AbstractCard c) {
        StampedMod modifier = getModifier(c);
        if (modifier == null)
            return 0;
        return modifier.amount;
    }

    public static boolean isStamped(AbstractCard c) {
        return getStamps(c) > 0;
    }

    @SpirePatch(clz=AbstractCard.class, method="initializeDescription")
    public static class AddKeyword {
        private static String STAMP_KEYWORD_ID = makeID("stamp");

        public static void Postfix(AbstractCard __instance) {
            if (getStamps(__instance) > 0 && !__instance.keywords.contains(STAMP_KEYWORD_ID))
                __instance.keywords.add(STAMP_KEYWORD_ID);
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="renderCardBg")
    public static class Render {
        private static final float GRIME_TIME = 0.4f;
        private static final float INITIAL_SCALE = 3.0f;
        private static Field colorField = ReflectionHacks.getCachedField(AbstractCard.class, "renderColor");
        private static TextureAtlas.AtlasRegion STAMP1_TEX = new TextureAtlas.AtlasRegion(new Texture(makeImagePath("512/stamp1.png")), 0, 0, 512, 512);
        private static TextureAtlas.AtlasRegion STAMP2_TEX = new TextureAtlas.AtlasRegion(new Texture(makeImagePath("512/stamp2.png")), 0, 0, 512, 512);
        private static TextureAtlas.AtlasRegion STAMP3_TEX = new TextureAtlas.AtlasRegion(new Texture(makeImagePath("512/stamp3.png")), 0, 0, 512, 512);

        public static void Postfix(AbstractCard __instance, SpriteBatch sb, float x, float y) {
            try {
                StampedMod stamps = getModifier(__instance);
                if (stamps != null) {
                    if (stamps.amount >= 1)
                        render(__instance, sb, STAMP1_TEX, stamps.timer1);
                    if (stamps.amount >= 2)
                        render(__instance, sb, STAMP2_TEX, stamps.timer2);
                    if (stamps.amount >= 3)
                        render(__instance, sb, STAMP3_TEX, stamps.timer3);
                }
            } catch (Exception e) {}
        }

        private static void render(AbstractCard c, SpriteBatch sb, TextureAtlas.AtlasRegion img, float timer) throws IllegalAccessException {
            float progress = Math.min(timer / GRIME_TIME, 1f);
            float scale = INITIAL_SCALE - (INITIAL_SCALE - 1f) * progress;
            sb.setColor(((Color)colorField.get(c)).cpy().mul(1f, 1f, 1f, progress));
            sb.draw(img, c.current_x - img.originalWidth / 2F, c.current_y - img.originalHeight / 2F, img.originalWidth / 2F, img.originalHeight / 2F, img.packedWidth, img.packedHeight, c.drawScale * Settings.scale * scale, c.drawScale * Settings.scale * scale, c.angle);
        }
    }
}