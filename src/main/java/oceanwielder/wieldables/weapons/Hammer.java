package oceanwielder.wieldables.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Hammer extends AbstractWeapon {
    public static String ID = makeID("Hammer");

    public Hammer() {
        super(ID, 6, 2, 5, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void use(AbstractCard c, AbstractMonster m) {
        super.use(c, m);
        baseDequipPower += secondary;
        applyPowers();
    }

    public void useVfx(AbstractMonster m) {
        vfxTop(new VerticalImpactEffect(m.hb.cX, m.hb.cY));
        vfxTop(new ThrowHammerEffect(this, m.hb.cX, m.hb.cY), 0.2f);
    }

    public void dequipEffect() {
        att(new DamageAllEnemiesAction(adp(), DamageInfo.createDamageMatrix(dequipPower, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        forAllMonstersLivingBackwards(mo -> vfxTop(new WeightyImpactEffect(mo.hb.cX, mo.hb.cY, new Color(0.5f, 0.4f, 0.3f, 1f)), mo == getEnemies().get(getEnemies().size()-1) ? 0.6f : 0f));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        description += strings.DESCRIPTION[0] + secondary + strings.DESCRIPTION[1] + dequipPower + strings.DESCRIPTION[2];
    }

    @Override
    protected String secondaryText() {
        return secondary + "->" + dequipPower;
    }

    private static class ThrowHammerEffect extends AbstractGameEffect {
        private static final float SPIN_SPEED = -800f;
        private static final float DURATION = 0.4f;

        public AbstractWeapon weapon;
        private float sX, sY, tX, tY;

        public ThrowHammerEffect(AbstractWeapon weapon, float tX, float tY) {
            this.weapon = weapon;
            this.tX = tX;
            this.tY = tY;
        }

        public void update() {
            if (duration == 0f) {
                rotation = weapon.angle;
                sX = weapon.hb.cX;
                sY = weapon.hb.cY;
            }
            duration += Gdx.graphics.getDeltaTime();
            float progress = duration / DURATION;
            float x = sX + (tX - sX) * progress;
            float y = sY + (tY - sY) * progress;
            weapon.animX = (x - weapon.cX) / Settings.scale;
            weapon.animY = (y - weapon.cY) / Settings.scale;
            rotation += SPIN_SPEED * Gdx.graphics.getDeltaTime();
            weapon.angle += rotation;
            if (progress >= 1f) {
                AbstractDungeon.effectsQueue.add(new ReturnHammerEffect(weapon, rotation));
                isDone = true;
            }
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }

    private static class ReturnHammerEffect extends AbstractGameEffect {
        private static final float DURATION = 0.6f;
        private static final float UPPIES = 150f;

        private AbstractWeapon weapon;
        private float sX = -100f, sY, startRotation;

        public ReturnHammerEffect(AbstractWeapon weapon, float startRotation) {
            this.weapon = weapon;
            this.startRotation = startRotation;
        }

        public void update() {
            if (weapon.dequipped) {
                isDone = true;
                return;
            }
            for (AbstractGameEffect effect : AbstractDungeon.effectList)
                if (effect instanceof ThrowHammerEffect && ((ThrowHammerEffect)effect).weapon == weapon || effect instanceof ReturnHammerEffect && effect != this) {
                    isDone = true;
                    return;
                }
            duration += Gdx.graphics.getDeltaTime();
            float progress = duration / DURATION;
            weapon.animX = sX * (1f - progress);
            weapon.animY = sY * (1f - progress) + UPPIES * (float)Math.sin(progress * Math.PI);
            weapon.animScale = progress;
            weapon.angle += startRotation * (1f - progress);
            if (progress >= 1f) {
                weapon.animX = 0f;
                weapon.animY = 0f;
                weapon.animScale = 1f;
                isDone = true;
            }
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }
}