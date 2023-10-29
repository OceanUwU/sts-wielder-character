package oceanwielder.wieldables.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class ThrowingStar extends AbstractWeapon {
    private static final float IDLE_SPIN_SPEED = -15f;
    public static String ID = makeID("ThrowingStar");

    public ThrowingStar() {
        super(ID, 7, -1, 2);
        shouldPopOut = false;
    }

    public void use(AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        vfxTop(new ThrowThrowingStarEffect(this, m.hb.cX, m.hb.cY, 0.2f));
    }

    public void dequipEffect() {
        forAllMonstersLivingBackwards(mo -> applyToEnemyTop(mo, new VulnerablePower(mo, dequipPower, false)));
        vfxTop(new ThrowThrowingStarEffect(this, Settings.WIDTH + hb.width, hb.cY, 0.5f));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle = vfxTimer * IDLE_SPIN_SPEED;
        animScale = MathHelper.fadeLerpSnap(animScale, 1.0f);
    }

    private static class ThrowThrowingStarEffect extends AbstractGameEffect {
        private static final float SPIN_SPEED = -150f;

        private ThrowingStar star;
        private float sX, sY, x, y, tX, tY;
        private float length;

        public ThrowThrowingStarEffect(ThrowingStar star, float tX, float tY, float duration) {
            this.star = star;
            this.tX = tX;
            this.tY = tY;
            color = Color.WHITE.cpy();
            length = duration;
        }

        public void update() {
            if (duration == 0f) {
                rotation = star.angle;
                sX = star.hb.cX;
                sY = star.hb.cY;
                star.animScale = 0f;
                star.vfxTimer = MathUtils.random(0f, 5f);
            }
            duration += Gdx.graphics.getDeltaTime();
            float progress = duration / length;
            x = sX + (tX - sX) * progress;
            y = sY + (tY - sY) * progress;
            if (progress >= 1f)
                isDone = true;
            else if (progress > 0.9f)
                color.a = 1 - (progress - 0.9f) / 0.1f;
            rotation += SPIN_SPEED * Gdx.graphics.getDeltaTime();
        }

        public void render(SpriteBatch sb) {
            sb.draw(star.texture, x - CENTRE, y - CENTRE, CENTRE, CENTRE, SIZE, SIZE, scale, scale, rotation);
        }

        public void dispose() {}
    }
}