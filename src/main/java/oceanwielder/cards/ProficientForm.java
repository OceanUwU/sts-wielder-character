package oceanwielder.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.GuardAction;
import oceanwielder.actions.HitAction;
import oceanwielder.actions.HitAllAction;
import oceanwielder.powers.AbstractWielderPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import basemod.helpers.BaseModCardTags;

public class ProficientForm extends AbstractWielderCard {
    public final static String ID = makeID(ProficientForm.class.getSimpleName());

    public ProficientForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(3, -1);
        tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new Power(p, magicNumber));
    }

    private static class Power extends AbstractWielderPower {
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ProficientForm.ID);

        public Power(AbstractCreature owner, int amount) {
            super(ProficientForm.ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
            amount2 = amount;
            isTwoAmount = true;
            updateDescription();
        }

        @Override
        public void onHit(AbstractCard c, AbstractMonster m, boolean notARepeat, HitAction action) {
            if (notARepeat) {
                if (--amount == 0) {
                    amount = amount2;
                    flash();
                    action.amount++;
                    action.additionalNonReal++;
                }
                updateDescription();
            }
        }

        @Override
        public void onHitAll(AbstractCard c, boolean notARepeat, HitAllAction action) {
            if (notARepeat) {
                if (--amount == 0) {
                    amount = amount2;
                    flash();
                    action.amount++;
                    action.additionalNonReal++;
                }
                updateDescription();
            }
        }

        @Override
        public void onGuard(AbstractCard c, AbstractMonster m, boolean notARepeat, GuardAction action) {
            if (notARepeat) {
                if (--amount == 0) {
                    amount = amount2;
                    flash();
                    action.amount++;
                    action.additionalNonReal++;
                }
                updateDescription();
            }
        }

        @Override
        public void updateDescription() {
            String[] d = powerStrings.DESCRIPTIONS;
            description = d[0] + amount2 + d[1] + amount + d[2];
        }
    }
}