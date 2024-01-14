package oceanwielder.actions;

import static oceanwielder.WielderMod.makeImagePath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import oceanwielder.util.TexLoader;
import oceanwielder.util.Tix;
import oceanwielder.util.WielderAudio;
import oceanwielder.util.Wiz;

public class GainTixAction extends AbstractGameAction {
    public GainTixAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        isDone = true;
        Tix.gain(amount);
        Tix.move();
        Tix.fontScale *= 1.5f;
        Wiz.playAudio(WielderAudio.TIX);
        for (int i = 0; i < 10 * amount; i++)
            AbstractDungeon.effectList.add(new TixSprayEffect());
    }

    public static class TixSprayEffect extends AbstractGameEffect {
        private static final int W = 50;
        private static final int H = 50;
        private static final TextureAtlas.AtlasRegion IMG = new TextureAtlas.AtlasRegion(TexLoader.getTexture(makeImagePath("vfx/tix.png")), 0, 0, W, H);

        private float x = Tix.x, y = Tix.y, oX, oY, rotation, shootOutTime, shootOutVel, shootOutDirection, timeScale, rotationScale, movementScale;
        private int xDir = MathUtils.random(0, 1) * 2 - 1;
        private boolean shooting = true;

        public TixSprayEffect() {
            scale *= MathUtils.random(0.5f, 0.85f);
            shootOutTime = MathUtils.random(0.2f, 0.8f);
            shootOutVel = MathUtils.random(100f, 750f) * Settings.scale;
            shootOutDirection = MathUtils.random(0f, 2f * (float)Math.PI);
            timeScale = MathUtils.random(0.85f, 1.15f);
            rotationScale = MathUtils.random(20f, 40f);
            movementScale = MathUtils.random(150f, 300f) * Settings.scale;
        }

        public void update() {
            duration += Gdx.graphics.getDeltaTime();
            if (shooting) {
                float vel = shootOutVel * (float)Math.cos(Math.PI * duration / shootOutTime / 2f);
                x += Math.cos(shootOutDirection) * Gdx.graphics.getDeltaTime() * vel;
                y += Math.sin(shootOutDirection) * Gdx.graphics.getDeltaTime() * vel;
                if (duration >= shootOutTime) {
                    shooting = false;
                    oX = x;
                    oY = y;
                    duration = 0f;
                }
            } else {
                x = oX + (float)Math.sin(duration * timeScale * Math.PI) * movementScale * xDir;
                y = oY + (0.6f * (float)Math.pow(Math.sin(duration * timeScale * Math.PI), 2) - 0.5f * duration * timeScale) * movementScale;
                rotation = (float)Math.sin(duration * timeScale * Math.PI) * rotationScale * xDir;
            }
            if (y < -100f * scale)
                isDone = true;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(Color.WHITE);
            sb.draw(IMG, x - W/2, y - H/2, W/2, H/2, W, H, scale, scale, rotation);
        }

        public void dispose() {}
    }
}