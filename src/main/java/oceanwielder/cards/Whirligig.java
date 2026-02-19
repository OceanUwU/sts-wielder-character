package oceanwielder.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.GuardAction;
import oceanwielder.powers.AbstractWielderPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Whirligig extends AbstractWielderCard {
    public final static String ID = makeID(Whirligig.class.getSimpleName());

    public Whirligig() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(5, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new Power(p, magicNumber));
    }

    private static class Power extends AbstractWielderPower implements NonStackablePower {
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(Whirligig.ID);

        public Power(AbstractCreature owner, int amount) {
            super(Whirligig.ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
            amount2 = amount;
            isTwoAmount = true;
            updateDescription();
        }

        @Override
        public void onGuard(AbstractCard c, AbstractMonster m, boolean fromRealCard, GuardAction action) {
            if (fromRealCard && --amount == 0) {
                amount = amount2;
                flash();
                action.amount++;
                action.additionalNonReal++;
            }
            updateDescription();
        }

        @Override
        public void updateDescription() {
            String[] d = powerStrings.DESCRIPTIONS;
            description = (amount2 == 1 ? d[0] : (d[1] + amount2 + d[2])) + amount + (amount == 1 ? d[3] : d[4]);
        }
    }
}