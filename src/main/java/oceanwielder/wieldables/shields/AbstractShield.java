package oceanwielder.wieldables.shields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import oceanwielder.wieldables.AbstractWieldable;

import static oceanwielder.util.Wiz.*;

public abstract class AbstractShield extends AbstractWieldable {
    public static final float X_OFFSET = 100f;
    public static final float Y_OFFSET = 180f;
    private static final float BOB_HEIGHT = 5f;
    private static final float BOB_TIME = 3.5f;
    private static AbstractCard sim;

    public AbstractShield(String id, int basePrimary, int baseSecondary, int baseDequipPower) {
        super(id, basePrimary, baseSecondary, baseDequipPower, X_OFFSET, Y_OFFSET);
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
        vfxTop(new ShieldPulseEffect(this));
    }

    protected void blck() {
        applyPowers();
        att(new GainBlockAction(adp(), primary, true));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        animY = (float)Math.sin(vfxTimer * Math.PI / BOB_TIME) * BOB_HEIGHT;
    }

    private static class ShieldPulseEffect extends AbstractGameEffect {
        private static final float DURATION = 0.4f;
        private static final float EXPANSION = 0.25f;
        private AbstractShield shield;

        public ShieldPulseEffect(AbstractShield shield) {
            this.shield = shield;
        }

        public void update() {
            duration += Gdx.graphics.getDeltaTime();
            shield.animScale = 1f + (float)Math.sin(duration / DURATION * Math.PI) * EXPANSION;
            if (duration >= DURATION) {
                isDone = true;
                shield.animScale = 1f;
            }
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }
}
