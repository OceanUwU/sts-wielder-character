package oceanwielder.wieldables.weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.wieldables.AbstractWieldable;

import static oceanwielder.util.Wiz.*;

public abstract class AbstractWeapon extends AbstractWieldable {
    private static AbstractCard sim;

    public AbstractWeapon(String id, int basePrimary, int baseSecondary, int baseDequipPower) {
        super(id, basePrimary, baseSecondary, baseDequipPower, 240f, 120f);
    }

    @Override
    public void applyPowers() {
        if (sim == null) {
            sim = new Strike_Red();
            sim.tags.clear();
        }
        super.applyPowers();
        sim.baseDamage = basePrimary;
        sim.applyPowers();
        primary = Math.max(sim.damage, 0);
        updateDescription();
    }

    public void calculateDamage(AbstractMonster mo) {
        if (sim == null) {
            sim = new Strike_Red();
            sim.tags.clear();
        }
        sim.baseDamage = basePrimary;
        sim.calculateCardDamage(mo);
        primary = sim.damage;
    }

    @Override
    public void use(AbstractMonster mo) {
        dmg(mo);
    }

    protected void dmg(AbstractMonster mo, AbstractGameAction.AttackEffect fx) {
        calculateDamage(mo);
        att(new DamageAction(mo, new DamageInfo(adp(), primary, DamageInfo.DamageType.NORMAL), fx, true));
    }

    protected void dmg(AbstractMonster mo) {
        dmg(mo, AbstractGameAction.AttackEffect.NONE);
    }
}
