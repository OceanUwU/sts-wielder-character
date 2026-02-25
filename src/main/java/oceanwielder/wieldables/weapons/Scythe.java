package oceanwielder.wieldables.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Scythe extends AbstractWeapon {
    public static String ID = makeID("Scythe");

    public Scythe() {
        super(ID, 5, 3, 1, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    public void use(AbstractCard c, AbstractMonster m) {
        att(new GainBlockAction(adp(), secondary));
        //applyToSelfTop(new AegisPower(adp(), secondary));
        super.use(c, m);
    }

    public void useOnAll(AbstractCard c) {
        att(new GainBlockAction(adp(), secondary));
        //applyToSelfTop(new AegisPower(adp(), secondary));
        super.useOnAll(c);
    }

    public void useVfx(AbstractMonster m) {
        vfxTop(new SwingWeaponEffect(this));
    }

    public void dequipEffect() {
        shouldPopOut = false;
        forAllMonstersLivingBackwards(mo -> applyToEnemyTop(mo, new StrengthPower(mo, -dequipPower)));
        forAllMonstersLivingBackwards(mo -> vfxTop(new FlashAtkImgEffect(mo.hb.cX, mo.hb.cY, attackEffect)));
        vfxTop(new ScytheReapEffect(this), ScytheReapEffect.DURATION_1 + 0.1f);
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        description += strings.DESCRIPTION[0] + secondary + strings.DESCRIPTION[1] + dequipPower + strings.DESCRIPTION[2];
    }

    private static class ScytheReapEffect extends AbstractGameEffect {
        private static final float DURATION_1 = 0.2f, DURATION_2 = 0.4f,
            TX_1 = 25f * Settings.scale, TY_1 = 210f * Settings.scale,
            TX_2 = 1000f * Settings.scale, TY_2 = -800f * Settings.scale,
            REAP_BACK_ANGLE = 40f,
            REAP_FORWARD_ANGLE = -90f;

        private AbstractWeapon weapon;
        private float sX, sY, x, y, initialAngle;

        public ScytheReapEffect(AbstractWeapon weapon) {
            this.weapon = weapon;
            color = Color.WHITE.cpy();
        }

        public void update() {
            if (duration == 0f) {
                sX = weapon.hb.cX;
                sY = weapon.hb.cY;
                initialAngle = weapon.angle;
            }
            duration += Gdx.graphics.getDeltaTime();

            float progress = Math.min(duration / DURATION_1, 1f);
            x = sX + (float)Math.sin(progress * 3.77f) * TX_1;
            y = sY + (float)Math.sin(progress * 0.79f) * TY_1;
            rotation = (float)Math.sin(2f * progress) * (REAP_BACK_ANGLE - initialAngle) + initialAngle;

            if (duration > DURATION_1) {
                progress = (duration - DURATION_1) / DURATION_2;
                rotation += (REAP_FORWARD_ANGLE - rotation) * progress;
                x += TX_2 * progress;
                y += TY_2 * Math.pow(progress, 1.75f);
                if (progress > 0.8f)
                    color.a = (1f - progress) / (1f - 0.8f);
                scale = (1f + progress * 2f) * Settings.scale;
            }

            if (duration > DURATION_1 + DURATION_2)
                isDone = true;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(Color.WHITE);
            sb.draw(weapon.texture, x - CENTRE, y - CENTRE, CENTRE, CENTRE, SIZE, SIZE, scale, scale, rotation);
        }

        public void dispose() {}
    }
}