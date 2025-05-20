package oceanwielder.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import oceanwielder.wieldables.WieldableLibrary;

public class WielderVictoryEffect extends AbstractGameEffect {
    private static final float WIELDABLE_INTERVAL = 0.2f;
    
    private ArrayList<FlingingWieldable> wieldables = new ArrayList<>();
    private float flingTimer, timer;

    @Override
    public void update() {
        flingTimer -= Gdx.graphics.getDeltaTime();
        timer += Gdx.graphics.getDeltaTime();
        while (flingTimer <= 0f) {
            wieldables.add(new FlingingWieldable());
            flingTimer += WIELDABLE_INTERVAL;
        }
        for (FlingingWieldable w : wieldables)
            w.update();
        wieldables.removeIf(w -> w.isDone);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1f, 1f, 1f, Math.min(timer, 1f)));
        sb.setBlendFunction(770, 771);
        for (FlingingWieldable w : wieldables)
            w.render(sb);
        sb.setBlendFunction(770, 1);
    }

    @Override public void dispose() {}

    private static class FlingingWieldable {
        private static final float SIZE = 192f;
        private static final float GRAVITY = 400f;
        public boolean isDone = false;
        private final TextureRegion img = WieldableLibrary.getRandomWieldable(false).texture;
        private float x = MathUtils.random(0f, Settings.WIDTH),
            y = - img.getRegionWidth() * 1.42f,
            startY = y,
            a = MathUtils.random() * (float)Math.PI * 2f, //angle
            xV = MathUtils.random(-200f, 200f) * Settings.scale,
            yV = MathUtils.random(500f, 1200f) * Settings.scale,
            aV = MathUtils.random(-0.8f, 0.8f)  * (float)Math.PI * 2f;

        public void update() {
            x += xV * Gdx.graphics.getDeltaTime();
            yV -= GRAVITY * Gdx.graphics.getDeltaTime() / 2f;
            y += yV * Gdx.graphics.getDeltaTime();
            yV -= GRAVITY * Gdx.graphics.getDeltaTime() / 2f;
            a += aV * Gdx.graphics.getDeltaTime();
            isDone = y < startY;
        }

        public void render(SpriteBatch sb) {
            sb.draw(img, x-SIZE/2, y-SIZE/2, SIZE/2, SIZE/2, SIZE, SIZE, Settings.scale, Settings.scale, a * MathUtils.radDeg);
        }
    }
}