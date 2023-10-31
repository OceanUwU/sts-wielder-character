package oceanwielder.wieldables.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Emeici extends AbstractWeapon {
    private static final float BOB_HEIGHT = 4.5f;
    private static final float BOB_TIME = 2.8f;
    private static final float BOB_OFFSET = 0.6f;
    private static final float X_OFFSET = 6f;
    private static final float Y_OFFSET = 24f;
    public static String ID = makeID("Emeici");

    private float animX1, animX2, animY1, animY2, angle2, xVel2, yVel2, dequipSpinSpeed2;
    private boolean left;

    public Emeici() {
        super(ID, 3, 1);
        primaryTimes = 2;
    }

    public void use(AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        vfxTop(new SpinEmeiciEffect(this, left));
        left = !left;
    }

    public void dequipEffect() {
        applyToSelfTop(new StrengthPower(adp(), dequipPower));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        if (dequipped && !handleDequipAnimation) {
            cX += xVel * Gdx.graphics.getDeltaTime() * Settings.scale;
            cY += yVel * Gdx.graphics.getDeltaTime() * Settings.scale;
            animX2 += xVel2 * Gdx.graphics.getDeltaTime();
            animY2 += yVel2 * Gdx.graphics.getDeltaTime();
            if (cY < -500f)
                done = true;
            angle += dequipSpinSpeed * Gdx.graphics.getDeltaTime();
            angle2 += dequipSpinSpeed2 * Gdx.graphics.getDeltaTime();
            yVel += Y_ACCELERATION * Gdx.graphics.getDeltaTime();
            yVel2 += Y_ACCELERATION * Gdx.graphics.getDeltaTime();
        } else {
            angle2 = -angle;
            animY1 = (float)Math.sin(vfxTimer * Math.PI / BOB_TIME) * BOB_HEIGHT;
            animY2 = (float)Math.sin((vfxTimer + BOB_OFFSET) * Math.PI / BOB_TIME) * BOB_HEIGHT;
        }
    }

    @Override
    protected void startDequipAnimation() {
        super.startDequipAnimation();
        xVel2 = xVel;
        yVel2 = yVel;
        dequipSpinSpeed2 = dequipSpinSpeed;
        super.startDequipAnimation();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        renderHelper(sb, -X_OFFSET - animX1, Y_OFFSET + animY1, angle);
        renderHelper(sb, X_OFFSET + animX2, -Y_OFFSET - animY2, angle2);
        hb.render(sb);
    }

    private static class SpinEmeiciEffect extends AbstractGameEffect {
        private static final float SPINNER_OFFSET = 30f;
        private static final float THRUST_DISTANCE = 20f;
        private static final float DURATION = 0.6f;
        private static final float ROTATE = 360f;
        private static final float MOVE_OUT_FRACTION = 0.2f;

        private Emeici emeici;
        private boolean left;

        public SpinEmeiciEffect(Emeici emeici, boolean left) {
            this.emeici = emeici;
            this.left = left;
        }

        public void update() {
            duration += Gdx.graphics.getDeltaTime();
            float progress = duration / DURATION;
            rotation = progress * ROTATE;
            float offset, thrust = THRUST_DISTANCE * (float)Math.abs(Math.sin(2 * Math.PI * progress));
            if (progress < MOVE_OUT_FRACTION) {
                offset = SPINNER_OFFSET * progress / MOVE_OUT_FRACTION;
            } else if (progress > 1f - MOVE_OUT_FRACTION) {
                offset = SPINNER_OFFSET * (1f - progress) / MOVE_OUT_FRACTION;
            } else {
                offset = SPINNER_OFFSET;
            }
            if (left) {
                emeici.animX1 = offset;
                emeici.animY1 += offset;
                emeici.angle += rotation;
                emeici.animX2 = thrust;
            } else {
                emeici.animX2 = offset;
                emeici.animY2 += offset;
                emeici.angle2 += rotation;
                emeici.animX1 = -thrust;
            }
            if (progress >= 1f) {
                emeici.animX1 = 0f;
                emeici.animX2 = 0f;
                isDone = true;
            }
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }
}