package oceanwielder.wieldables.weapons;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import oceanwielder.powers.HitUpPower;
import oceanwielder.util.Wiz;
import oceanwielder.wieldables.AbstractWieldable;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public abstract class AbstractWeapon extends AbstractWieldable {
    public static final float X_OFFSET = 240f;
    public static final float Y_OFFSET = 120f;
    private static final float TWIST_ANGLE = 6f;
    private static final float TWIST_TIME = 3f;
    private static AbstractCard sim;
    protected static OrbStrings baseStrings = CardCrawlGame.languagePack.getOrbString(makeID("Weapon"));

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
    public void applyPowers(AbstractCard c) {
        super.applyPowers(c);
        if (c == null) c = getSim();
        int base = c.baseDamage;
        c.baseDamage = basePrimary;
        c.applyPowers();
        c.baseDamage = base;
        primary = Math.max(c.damage, 0);
        primaryTimes += Wiz.pwrAmt(adp(), HitUpPower.POWER_ID);
        updateDescription();
    }

    private AbstractCard getSim() {
        if (sim == null) {
            sim = new Strike_Red();
            sim.baseDamage = 0;
            sim.tags.clear();
        }
        return sim;
    }

    public int calculateDamage(AbstractMonster m) {
        return calculateDamage(null, m);
    }

    public int calculateDamage(AbstractCard c, AbstractMonster m) {
        if (c == null) c = getSim();
        int base = c.baseDamage;
        c.baseDamage += basePrimary;
        c.calculateCardDamage(m);
        c.baseDamage = base;
        primary = c.damage;
        return primary;
    }

    @Override
    public void use(AbstractCard c, AbstractMonster m) {
        if (m == null) 
            m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (m.isDeadOrEscaped()) return;
        dmg(c, m);
        useVfx(m);
    }

    @Override
    public void useOnAll(AbstractCard c) {
        dmgAll(c);
        if (vfxCanAffectAll)
            forAllMonstersLivingBackwards(mo -> useVfx(mo));
        else
            useVfx(getEnemies().get(getEnemies().size()-1));
    }

    abstract void useVfx(AbstractMonster m);

    private void dmg(AbstractCard c, AbstractMonster m) {
        calculateDamage(c, m);
        for (int i = 0; i < primaryTimes; i++)
            att(new DamageAction(m, new DamageInfo(adp(), primary, DamageInfo.DamageType.NORMAL), attackEffect, true));
    }

    private void dmgAll(AbstractCard c) {
        for (int i = 0; i < primaryTimes; i++) {
            int[] dmg = AbstractDungeon.getCurrRoom().monsters.monsters.stream().mapToInt(m -> calculateDamage(c, m)).toArray();
            AbstractGameAction action = new DamageAllEnemiesAction(adp(), dmg, DamageInfo.DamageType.NORMAL, attackEffect);
            ReflectionHacks.setPrivate(action, AbstractGameAction.class, "duration", Settings.ACTION_DUR_XFAST);
            att(action);
        }
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle = (float)Math.sin(vfxTimer * Math.PI / TWIST_TIME) * TWIST_ANGLE + angleOffset;
    }

    @Override
    public void updateDescription() {
        if (primaryTimes == 1)
            description = baseStrings.DESCRIPTION[0] + primary + baseStrings.DESCRIPTION[1];
        else
            description = baseStrings.DESCRIPTION[0] + primary + baseStrings.DESCRIPTION[2] + primaryTimes + baseStrings.DESCRIPTION[3];
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
