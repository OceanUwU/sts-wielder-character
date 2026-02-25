package oceanwielder.wieldables.shields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import oceanwielder.powers.GuardUpPower;
import oceanwielder.util.Wiz;
import oceanwielder.wieldables.AbstractWieldable;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public abstract class AbstractShield extends AbstractWieldable {
    public static final float X_OFFSET = 100f;
    public static final float Y_OFFSET = 180f;
    private static final float BOB_HEIGHT = 5f;
    private static final float BOB_TIME = 3.5f;
    protected static AbstractCard sim;
    protected static OrbStrings baseStrings = CardCrawlGame.languagePack.getOrbString(makeID("Shield"));

    public AbstractShield(String id, int basePrimary, int baseSecondary, int baseDequipPower) {
        super(id, basePrimary, baseSecondary, baseDequipPower, X_OFFSET, Y_OFFSET);
    }

    public AbstractShield(String id, int basePrimary, int baseDequipPower) {
        this(id, basePrimary, -1, baseDequipPower);
    }

    @Override
    public void applyPowers(AbstractCard c) {
        if (c == null) {
            if (sim == null) {
                sim = new Defend_Red();
                sim.baseBlock = 0;
                sim.tags.clear();
            }
            c = sim;
        }
        super.applyPowers(c);
        int base = c.baseBlock;
        c.baseBlock += basePrimary;
        c.applyPowers();
        c.baseBlock = base;
        primary = Math.max(c.block, 0);
        primaryTimes += Wiz.pwrAmt(adp(), GuardUpPower.POWER_ID);
        updateDescription();
    }

    @Override
    public void use(AbstractCard c, AbstractMonster m) {
        blck(c);
        vfxTop(new WieldablePulseEffect(this));
    }

    protected void blck(AbstractCard c) {
        applyPowers(c);
        for (int i = 0; i < primaryTimes; i++)
            att(new GainBlockAction(adp(), primary, true));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        animY = (float)Math.sin(vfxTimer * Math.PI / BOB_TIME) * BOB_HEIGHT;
    }

    @Override
    public void updateDescription() {
        if (primaryTimes == 1)
            description = baseStrings.DESCRIPTION[0] + primary + baseStrings.DESCRIPTION[1];
        else
            description = baseStrings.DESCRIPTION[0] + primary + baseStrings.DESCRIPTION[2] + primaryTimes + baseStrings.DESCRIPTION[3];
    }
}
