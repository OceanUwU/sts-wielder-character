package oceanwielder.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
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
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
        setCostUpgrade(-1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new Power(p, magicNumber));
    }

    private static class Power extends AbstractWielderPower {
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(Whirligig.ID);

        public Power(AbstractCreature owner, int amount) {
            super(Whirligig.ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }

        @Override
        public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
            if (!isPlayer) return;
            flash();
            atb(new GuardAction(amount, null, false));
        }

        @Override
        public void updateDescription() {
            String[] d = powerStrings.DESCRIPTIONS;
            description = d[0] + amount + (amount == 1 ? d[1] : d[2]);
        }
    }
}