package oceanwielder.wieldables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import oceanwielder.characters.TheWielder;
import oceanwielder.wieldables.weapons.AbstractWeapon;
import java.util.ArrayList;

public class WieldableSlot {
    private static final float PREVIEW_OFFSET_Y = 164f;

    public boolean shouldRender = false;
    public ArrayList<AbstractWieldable> dequipped = new ArrayList<>();
    public AbstractWieldable wieldable;
    private AbstractWieldable defaultWieldable;
    private AbstractWieldable previewWieldable;

    public WieldableSlot(AbstractWieldable defaultWieldable) {
        this.defaultWieldable = defaultWieldable;
        wieldable = reset();
    }

    public AbstractWieldable reset() {
        shouldRender = AbstractDungeon.player instanceof TheWielder;
        dequipped.clear();
        wieldable = defaultWieldable.makeCopy();
        previewWieldable = new UnknownWieldable(defaultWieldable instanceof AbstractWeapon);
        stopPreviewing();
        return wieldable;
    }

    public void preview(AbstractWieldable toPreview) {
        shouldRender = true;
        previewWieldable = toPreview;
        previewWieldable.targetFontScale = AbstractWieldable.DEFAULT_FONT_SCALE;
        if (previewWieldable.fontScale <= 0.03f)
            previewWieldable.fontScale = 0.05f;
        previewWieldable.targetAlpha = 0.5f;
        previewWieldable.previewOffsetY = PREVIEW_OFFSET_Y;
        wieldable.showDequipValue();
    }

    public void stopPreviewing() {
        previewWieldable.targetFontScale = 0f;
        previewWieldable.targetAlpha = 0f;
        wieldable.hideDequipValue();
    }

    public void wield(AbstractWieldable newWieldable) {
        AbstractWieldable toWield = newWieldable.makeCopy();
        previewWieldable.targetFontScale = 0f;
        previewWieldable.targetAlpha = 0f;
        toWield.previewOffsetY = previewWieldable.previewOffsetY;
        if (previewWieldable instanceof UnknownWieldable) {
            toWield.fontScale = 0f;
            toWield.color.a = 0f;
        } else {
            toWield.fontScale = previewWieldable.fontScale;
            toWield.color.a = previewWieldable.color.a;
            toWield.vfxTimer = previewWieldable.vfxTimer;
            previewWieldable.fontScale = 0.03f;
            previewWieldable.color.a = previewWieldable.targetAlpha;
        }
        wieldable.dequip();
        if (wieldable.shouldPopOut)
            dequipped.add(wieldable);
        wieldable = toWield;
        AbstractDungeon.effectList.add(new InsertWieldableEffect(wieldable));
    }

    public void update() {
        wieldable.update();
        for (AbstractWieldable w : dequipped)
            if (w != wieldable)
                w.update();
        for (AbstractWieldable w : dequipped)
            if (w.done) {
                dequipped.remove(w);
                break;
            }
        if (previewWieldable.fontScale > 0.03f)
            previewWieldable.update();
    }

    public void updateAnimation() {
        wieldable.updateAnimation();
        for (AbstractWieldable w : dequipped)
            if (w != wieldable)
                w.updateAnimation();
        if (previewWieldable.fontScale > 0.03f)
            previewWieldable.updateAnimation();
    }

    public void render(SpriteBatch sb) {
        wieldable.render(sb);
        for (AbstractWieldable w : dequipped)
            if (w != wieldable)
                w.render(sb);
        wieldable.renderText(sb);
        if (previewWieldable.fontScale > 0.03f) {
            previewWieldable.render(sb);
            previewWieldable.renderText(sb);
        }
    }

    private static class InsertWieldableEffect extends AbstractGameEffect {
        private static final float DURATION = 0.6f;

        private AbstractWieldable wieldable;
        private float sY;

        public InsertWieldableEffect(AbstractWieldable wieldable) {
            this.wieldable = wieldable;
            sY = wieldable.previewOffsetY;
            duration = DURATION;
        }

        public void update() {
            duration -= Gdx.graphics.getDeltaTime();
            wieldable.previewOffsetY = -(float)(Math.cos(Math.PI * duration / DURATION) - 1f) / 2f * sY;
            if (duration <= 0f) {
                wieldable.previewOffsetY = 0f;
                isDone = true;
            }
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }
}
