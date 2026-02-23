package oceanwielder.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import java.util.ArrayList;
import oceanwielder.powers.AbstractWielderPower;
import oceanwielder.relics.TotemWithATopHat;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;
import static oceanwielder.util.Wiz.*;

import basemod.BaseMod;

public class Tix {
    private static final Texture DEFAULT_IMG = TexLoader.getTexture(makeImagePath("ui/tix.png"));
    private static final Texture HOVERED_IMG = TexLoader.getTexture(makeImagePath("ui/tixhovered.png"));
    private static final Texture PRESSED_IMG = TexLoader.getTexture(makeImagePath("ui/tixpressed.png"));
    private static final float SIZE = DEFAULT_IMG.getWidth() * Settings.scale;
    private static final float OFFSET = 48f * Settings.scale;
    private static final float NUM_OFFSET_X = 32f * Settings.scale;
    private static final float NUM_OFFSET_Y = 0f * Settings.scale;
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("TixTip")).TEXT;
    private static final float DEFAULT_FONT_SCALE = 0.65f;
    public static float fontScale = DEFAULT_FONT_SCALE;
    private static Texture img;
    private static boolean shouldRender = false;
    public static int amt;
    private static Color color = Color.WHITE.cpy();
    private static float targetAlpha = color.a;
    private static Hitbox hb = new Hitbox(SIZE, SIZE);
    public static float x, y;
    public static int spentThisCombat, spentThisTurn;

    private static void reset() {
        amt = 0;
        targetAlpha = 0f;
        color.a = targetAlpha;
        spentThisCombat = spentThisTurn = 0;
    }

    public static void move() {
        x = AbstractDungeon.overlayMenu.energyPanel.current_x - OFFSET;
        y = AbstractDungeon.overlayMenu.energyPanel.current_y + OFFSET;
        hb.update(x - SIZE / 2f, y - SIZE / 2f);
    }

    private static void update() {
        move();
        if (hb.hovered) {
            TipHelper.renderGenericTip(x + 50f * Settings.scale, y, TEXT[0], TEXT[1]);
            img = HOVERED_IMG;
            if (InputHelper.justClickedLeft && amt > 0)
                CardCrawlGame.sound.play("UI_CLICK_1");
            if (InputHelper.isMouseDown) {
                img = PRESSED_IMG;
            } else if (InputHelper.justReleasedClickLeft && amt > 0) {
                actB(() -> {
                    if (adp().hand.group.size() >= BaseMod.MAX_HAND_SIZE)
                        adp().createHandIsFullDialog();
                    else if (amt > 0) {
                        int drawn = 1;
                        for (AbstractPower p : adp().powers)
                            if (p instanceof AbstractWielderPower)
                                drawn = ((AbstractWielderPower)p).changeCardsDrawnByTix(drawn);
                        if (adp().hasRelic(TotemWithATopHat.ID))
                            att(actionify(() -> { adp().getRelic(TotemWithATopHat.ID).flash(); }), new GainEnergyAction(drawn), actionify(() -> onSpendTix()));
                        else
                            att(new DrawCardAction(drawn, actionify(() -> {
                                if (DrawCardAction.drawnCards.size() > 0)
                                    onSpendTix();
                            })));
                    }
                });
            }
        } else
            img = DEFAULT_IMG;
        color.a = MathHelper.fadeLerpSnap(color.a, targetAlpha);
        fontScale = MathHelper.scaleLerpSnap(fontScale, DEFAULT_FONT_SCALE);
        if (color.a <= 0f)
            shouldRender = false;
    }

    private static void onSpendTix() {
        spentThisCombat++;
        spentThisTurn++;
        fontScale *= 1.5f;
        amt--;
        if (amt <= 0)
            targetAlpha = 0f;
        for (AbstractPower p : adp().powers)
            if (p instanceof AbstractWielderPower)
                ((AbstractWielderPower)p).onSpendTix((ArrayList<AbstractCard>)DrawCardAction.drawnCards.clone());
        adp().hand.group.forEach(c -> c.applyPowers());
    }

    private static void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(img, x - SIZE / 2f, y - SIZE / 2f, SIZE, SIZE);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(amt), x + NUM_OFFSET_X, y + NUM_OFFSET_Y, color, fontScale);
    }

    public static void gain(int amount) {
        amt += amount;
        if (!shouldRender) {
            shouldRender = true;
            targetAlpha = 1f;
        }
    }

    @SpirePatch(clz=EnergyPanel.class, method="renderOrb", paramtypez={SpriteBatch.class})
    public static class RenderPatch {
        public static void Postfix(EnergyPanel __instance, SpriteBatch sb) {
            if (shouldRender)
                render(sb);
        }
    }

    @SpirePatch(clz=EnergyPanel.class, method="update")
    public static class UpdatePatch {
        public static void Prefix() {
            if (shouldRender)
                update();
        }
    }

    @SpirePatch(clz=EnergyManager.class, method="prep")
    public static class ResetPatch {
        public static void Postfix() {
            reset();
        }
    }

    @SpirePatch(clz=GameActionManager.class, method="getNextAction")
    public static class ResetTrackedTurn {
        @SpireInsertPatch(loc=434)
        public static void Insert() {
            spentThisTurn = 0;
        }
    }
}