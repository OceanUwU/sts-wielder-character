package oceanwielder.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.colorless.Violence;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

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
    private static final float NUM_OFFSET_X = 21f * Settings.scale;
    private static final float NUM_OFFSET_Y = -18f * Settings.scale;
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("TixTip")).TEXT;
    private static final float FONT_SCALE = 0.65f;
    private static Texture img;
    private static boolean shouldRender = false;
    public static int amt;
    private static Color color = Color.WHITE.cpy();
    private static float targetAlpha = color.a;
    private static Hitbox hb = new Hitbox(SIZE, SIZE);
    private static float x, y;
    private static boolean wasClicked = false;

    private static void reset() {
        amt = 0;
        targetAlpha = 0f;
        color.a = targetAlpha;
    }

    private static void update() {
        x = AbstractDungeon.overlayMenu.energyPanel.current_x + OFFSET;
        y = AbstractDungeon.overlayMenu.energyPanel.current_y - OFFSET;
        hb.update(x - SIZE / 2f, y - SIZE / 2f);
        if (hb.hovered) {
            TipHelper.renderGenericTip(x + 50f * Settings.scale, y, TEXT[0], TEXT[1]);
            img = HOVERED_IMG;
            if (InputHelper.justClickedLeft && amt > 0)
                CardCrawlGame.sound.play("UI_CLICK_1");
            if (InputHelper.isMouseDown) {
                img = PRESSED_IMG;
                wasClicked = true;
            } else if (InputHelper.justReleasedClickLeft && amt > 0) {
                actB(() -> {
                    if (adp().hand.group.size() >= BaseMod.MAX_HAND_SIZE)
                        adp().createHandIsFullDialog();
                    else {
                        att(new DrawCardAction(1));
                        amt--;
                        if (amt <= 0)
                            targetAlpha = 0f;
                    }
                });
            }
        } else {
            wasClicked = false;
            img = DEFAULT_IMG;
        }
        color.a = MathHelper.fadeLerpSnap(color.a, targetAlpha);
        if (color.a <= 0f)
            shouldRender = false;
    }

    private static void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(img, x - SIZE / 2f, y - SIZE / 2f, SIZE, SIZE);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(amt), x + NUM_OFFSET_X, y + NUM_OFFSET_Y, color, FONT_SCALE);
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
}