package oceanwielder.wieldables.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import oceanwielder.wieldables.AbstractWieldable;

import static oceanwielder.util.Wiz.*;

public abstract class AbstractWeapon extends AbstractWieldable {
    public static final float X_OFFSET = 240f;
    public static final float Y_OFFSET = 120f;
    private static final float TWIST_ANGLE = 6f;
    private static final float TWIST_TIME = 3f;
    private static AbstractCard sim;

    protected AbstractGameAction.AttackEffect attackEffect = AbstractGameAction.AttackEffect.NONE;
    protected boolean vfxCanAffectAll = false;

    public AbstractWeapon(String id, int basePrimary, int baseSecondary, int baseDequipPower, AbstractGameAction.AttackEffect attackEffect) {
        super(id, basePrimary, baseSecondary, baseDequipPower, X_OFFSET, Y_OFFSET);
        this.attackEffect = attackEffect;
    }

    public AbstractWeapon(String id, int basePrimary, int baseDequipPower, AbstractGameAction.AttackEffect attackEffect) {
        this(id, basePrimary, -1, baseDequipPower, attackEffect);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        getSim().baseDamage = basePrimary;
        sim.applyPowers();
        primary = Math.max(sim.damage, 0);
        updateDescription();
    }

    private AbstractCard getSim() {
        if (sim == null) {
            sim = new Strike_Red();
            sim.tags.clear();
        }
        return sim;
    }

    public void calculateDamage(AbstractMonster m) {
        getSim().baseDamage = basePrimary;
        sim.calculateCardDamage(m);
        primary = sim.damage;
    }

    @Override
    public void use(AbstractMonster m) {
        dmg(m);
        useVfx(m);
    }

    @Override
    public void useOnAll() {
        dmgAll();
        if (vfxCanAffectAll)
            forAllMonstersLivingBackwards(mo -> useVfx(mo));
        else
            useVfx(getEnemies().get(getEnemies().size()-1));
    }

    abstract void useVfx(AbstractMonster m);

    private void dmg(AbstractMonster m) {
        calculateDamage(m);
        for (int i = 0; i < primaryTimes; i++)
            att(new DamageAction(m, new DamageInfo(adp(), primary, DamageInfo.DamageType.NORMAL), attackEffect, true));
    }

    private void dmgAll() {
        att(new DamageAllEnemiesAction(adp(), primary, DamageInfo.DamageType.NORMAL, attackEffect));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle = (float)Math.sin(vfxTimer * Math.PI / TWIST_TIME) * TWIST_ANGLE + angleOffset;
    }

    protected static class SwingWeaponEffect extends AbstractGameEffect {
        private static final float DURATION = 0.4f;
        private static final float ROTATE = -60f;

        private AbstractWeapon weapon;

        public SwingWeaponEffect(AbstractWeapon weapon) {
            this.weapon = weapon;
        }

        public void update() {
            duration += Gdx.graphics.getDeltaTime();
            float progress = duration / DURATION;
            if (progress >= 1f) {
                isDone = true;
                //weapon.angleOffset = 0f;
            } else
                weapon.angle += (float)Math.sin(Math.PI * progress) * ROTATE;
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }
}
