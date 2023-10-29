package oceanwielder.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static oceanwielder.WielderMod.makeID;

public class AegisPower extends AbstractWielderPower {
    public static String POWER_ID = makeID("AegisPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public AegisPower(AbstractCreature owner, int amount) {
        super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
    }
    
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
  
    public float modifyBlock(float blockAmount) {
        if ((blockAmount += amount) < 0f)
            return 0f;
        return blockAmount;
    }
  
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.baseBlock >= 0) {
            flash();
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        } 
    }
}