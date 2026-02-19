package oceanwielder.powers;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.SilentGainPowerEffect;
import java.util.ArrayList;
import oceanwielder.relics.GravShoes;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Weight extends AbstractWielderPower {
    public static String POWER_ID = makeID("Weight");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final int THRESHOLD = 3;
    private static final int HP_LOST = 6;
    private static final float FLASH_INTERVAL = 2f;

    private float flashTimer = 0f;

    public Weight(AbstractCreature owner, int amount) {
        super(POWER_ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, amount);
        ReflectionHacks.setPrivate(this, AbstractPower.class, "greenColor", ReflectionHacks.getPrivate(this, AbstractPower.class, "redColor"));
    }
   
    public void atEndOfTurn(boolean isPlayer) {
        if (amount >= THRESHOLD) {
            flash();
            final int weight = amount;
            int hpToLose = HP_LOST;
            for (AbstractRelic r : adp().relics)
                if (r instanceof GravShoes) {
                    AbstractRelic relic = adp().getRelic(GravShoes.ID);
                    relic.flash();
                    atb(new RelicAboveCreatureAction(owner, relic));
                    hpToLose = Math.max(0, hpToLose - GravShoes.POWER);
                }
            atb(
                new LoseHPAction(owner, owner, hpToLose),
                new RemoveSpecificPowerAction(owner, owner, this),
                actionify(() -> {
                    for (AbstractPower p : owner.powers)
                        if (p instanceof AbstractWielderPower)
                            ((AbstractWielderPower)p).onLoseHPFromWeight(weight);
                })
            );
        }
    }
    
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + THRESHOLD + powerStrings.DESCRIPTIONS[2] + HP_LOST + powerStrings.DESCRIPTIONS[3];
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if (amount >= THRESHOLD) {
            flashTimer -= Gdx.graphics.getDeltaTime();
            if (flashTimer < 0) {
                ((ArrayList<AbstractGameEffect>)ReflectionHacks.getPrivate(this, AbstractPower.class, "effect")).add(new SilentGainPowerEffect(this));
                flashTimer += FLASH_INTERVAL;
            }
        }
    }
}