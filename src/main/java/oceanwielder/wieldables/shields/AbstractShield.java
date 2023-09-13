package oceanwielder.wieldables.shields;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.wieldables.AbstractWieldable;

import static oceanwielder.util.Wiz.*;

public abstract class AbstractShield extends AbstractWieldable {
    private static AbstractCard sim;

    public AbstractShield(String id, int basePrimary, int baseSecondary, int baseDequipPower) {
        super(id, basePrimary, baseSecondary, baseDequipPower, 100f, 180f);
    }

    @Override
    public void applyPowers() {
        if (sim == null) {
            sim = new Defend_Red();
            sim.tags.clear();
        }
        super.applyPowers();
        sim.baseBlock = basePrimary;
        sim.applyPowers();
        primary = Math.max(sim.block, 0);
        updateDescription();
    }

    @Override
    public void use(AbstractMonster mo) {
        blck();
    }

    protected void blck() {
        applyPowers();
        att(new GainBlockAction(adp(), primary, true));
    }
}
