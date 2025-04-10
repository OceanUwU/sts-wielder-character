package oceanwielder.wieldables.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.lwjgl.Sys;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Chainsaw extends AbstractWeapon {
    public static String ID = makeID("Chainsaw");
    private static TextureRegion TOOTH_IMG = getTexture(ID + "Tooth");
    private static final int NUM_TEETH = 14;
    private static final float TOOTH_SPEED_STATIONARY = 0.02f;
    private static final float TOOTH_SPEED_ATTACKING = 0.90f;

    private float toothProgress = 0f;
    private float toothSpeed = TOOTH_SPEED_STATIONARY;

    public Chainsaw() {
        super(ID, 2, 2, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        primaryTimes = 3;
    }

    public void useVfx(AbstractMonster m) {
        vfxTop(new SwingWeaponEffect(this));
    }

    public void dequipEffect() {
        applyToSelfTop(new StrengthPower(adp(), dequipPower));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        for (int i = 0; i < NUM_TEETH; i++) {
            float toothAngle = toothProgress * (float)Math.PI * 2f + (float)i / (float)NUM_TEETH * (float)Math.PI * 2f;
            Vector2 toothPos = new Vector2(73.5f * (float)Math.cos(toothAngle), 21.21f * (float)Math.sin(toothAngle)).rotate(angle + 45);
            float toothRotation = toothPos.angle() - 90;
            sb.draw(TOOTH_IMG, cX - 12 + (animX + toothPos.x) * Settings.scale, cY - 12 + (animY + previewOffsetY + toothPos.y) * Settings.scale, 12, 12, 24, 24, Settings.scale * animScale, Settings.scale * animScale, toothRotation);
        }
        renderHelper(sb, 0, 0, angle);
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        toothProgress -= toothSpeed * Gdx.graphics.getDeltaTime();
        while (toothProgress < 0f)
            toothProgress += 1f;
    }

    protected static class SwingWeaponEffect extends AbstractGameEffect {
        private static final float DURATION = 0.5f;
        private static final float ROTATE = -50f;
        private static final float ROTATE_TIME = 0.15f;

        private Chainsaw chainsaw;

        public SwingWeaponEffect(Chainsaw weapon) {
            this.chainsaw = weapon;
        }

        public void update() {
            duration += Gdx.graphics.getDeltaTime();
            float progress = duration / DURATION;
            if (progress >= 1f) {
                isDone = true;
                chainsaw.toothSpeed = TOOTH_SPEED_STATIONARY;
                chainsaw.animX = 0;
                chainsaw.animY = 0;
            } else {
                float rotateProgress = 1f;
                if (duration < ROTATE_TIME) {
                    rotateProgress = 1f - (float)Math.pow(1f - duration / ROTATE_TIME, 2);
                } else if (duration > DURATION - ROTATE_TIME) {
                    rotateProgress = 1f - (float)Math.pow(1f - ((DURATION - duration) / ROTATE_TIME), 2);
                }
                chainsaw.angle += (float)Math.sin(Math.PI * rotateProgress / 2f) * ROTATE;
                chainsaw.toothSpeed = MathUtils.lerp(TOOTH_SPEED_STATIONARY, TOOTH_SPEED_ATTACKING, rotateProgress);
                chainsaw.animX = MathUtils.random(-2f, 2f) * rotateProgress;
                chainsaw.animY = MathUtils.random(-2f, 2f) * rotateProgress;
            }
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }
}