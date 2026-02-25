package oceanwielder.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.cards.AbstractWielderCard;
import oceanwielder.cards.Perpetuity;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.atb;

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
        if (!(card instanceof AbstractWielderCard) && card.baseBlock >= 0) {
            flash();
            if (owner.hasPower(Perpetuity.ID)) {
                atb(new ReducePowerAction(owner, owner, Perpetuity.ID, 1));
                return;
            }
            addToBot(new ReducePowerAction(owner, owner, this, amount));
        } 
    }

    @Override
    public void onGuard(AbstractCard c, AbstractMonster m, boolean fromCard) {
        if (!fromCard) return;
        flash();
        if (owner.hasPower(Perpetuity.ID)) {
            atb(new ReducePowerAction(owner, owner, Perpetuity.ID, 1));
            return;
        }
        addToBot(new ReducePowerAction(owner, owner, this, amount));
    }
}