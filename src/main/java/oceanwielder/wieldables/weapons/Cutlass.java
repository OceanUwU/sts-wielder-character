package oceanwielder.wieldables.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Cutlass extends AbstractWeapon {
    public static String ID = makeID("Cutlass");

    public Cutlass() {
        super(ID, 6, 9, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    public void useVfx(AbstractMonster m) {
        vfxTop(new SwingWeaponEffect(this));
    }

    public void dequipEffect() {
        target = getRandomTarget();
        if (target != null) {
            att(new DamageAction(target, new DamageInfo(adp(), dequipPower, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
            shouldPopOut = false;
            vfxTop(new ChuckSwordEffect(this, target.hb.cX, target.hb.cY), 0.2f);
        }
    }

    private static class ChuckSwordEffect extends AbstractGameEffect {
        private static final float MOVE_SPEED = 1200f * Settings.scale;
        private static final float SPIN_SPEED = -450f;

        private AbstractWeapon weapon;
        private float x, y, tX, tY, dX, dY;

        public ChuckSwordEffect(AbstractWeapon weapon, float tX, float tY) {
            this.weapon = weapon;
            this.tX = tX;
            this.tY = tY;
        }

        public void update() {
            if (duration == 0f) {
                rotation = weapon.angle;
                x = weapon.hb.cX;
                y = weapon.hb.cY;
                float angle = (float)Math.atan2(tY-y, tX-x);
                dX = (float)Math.cos(angle) * MOVE_SPEED;
                dY = (float)Math.sin(angle) * MOVE_SPEED;
                weapon.animScale = 0f;
                weapon.vfxTimer = MathUtils.random(0f, 5f);
            }
            duration += Gdx.graphics.getDeltaTime();
            x += dX * Gdx.graphics.getDeltaTime();
            y += dY * Gdx.graphics.getDeltaTime();
            rotation += SPIN_SPEED * Gdx.graphics.getDeltaTime();
            if (x - 200f > Settings.WIDTH || x + 200f < 0f || y - 200f > Settings.HEIGHT || y + 200f < 0f)
                isDone = true;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(Color.WHITE);
            sb.draw(weapon.texture, x - CENTRE, y - CENTRE, CENTRE, CENTRE, SIZE, SIZE, scale, scale, rotation);
        }

        public void dispose() {}
    }
}