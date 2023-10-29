package oceanwielder.wieldables.weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.wieldables.AbstractWieldable;

import static oceanwielder.util.Wiz.*;

public abstract class AbstractWeapon extends AbstractWieldable {
    public static final float X_OFFSET = 240f;
    public static final float Y_OFFSET = 120f;
    private static final float TWIST_ANGLE = 6f;
    private static final float TWIST_TIME = 3f;
    private static AbstractCard sim;

    public AbstractWeapon(String id, int basePrimary, int baseSecondary, int baseDequipPower) {
        super(id, basePrimary, baseSecondary, baseDequipPower, X_OFFSET, Y_OFFSET);
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

    public void calculateDamage(AbstractMonster mo) {
        getSim().baseDamage = basePrimary;
        sim.calculateCardDamage(mo);
        primary = sim.damage;
    }

    @Override
    public void use(AbstractMonster mo) {
        dmg(mo);
    }

    @Override
    public void useOnAll() {
        dmgAll();
    }

    protected void dmg(AbstractMonster mo, AbstractGameAction.AttackEffect fx) {
        calculateDamage(mo);
        for (int i = 0; i < primaryTimes; i++)
            att(new DamageAction(mo, new DamageInfo(adp(), primary, DamageInfo.DamageType.NORMAL), fx, true));
    }

    protected void dmg(AbstractMonster mo) {
        dmg(mo, AbstractGameAction.AttackEffect.NONE);
    }

    protected void dmgAll(AbstractGameAction.AttackEffect fx) {
        att(new DamageAllEnemiesAction(adp(), primary, DamageInfo.DamageType.NORMAL, fx));
    }

    protected void dmgAll() {
        dmgAll(AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle = (float)Math.sin(vfxTimer * Math.PI / TWIST_TIME) * TWIST_ANGLE + angleOffset;
    }
}
