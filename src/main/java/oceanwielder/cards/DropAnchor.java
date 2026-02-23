package oceanwielder.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import oceanwielder.powers.AbstractWielderPower;
import oceanwielder.powers.Weight;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class DropAnchor extends AbstractWielderCard {
    public final static String ID = makeID(DropAnchor.class.getSimpleName());

    public DropAnchor() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setHits(1);
        setMagic(1);
        setSecondMagic(0, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        applyToSelf(new Power(p, 1));
        if (secondMagic > 0)
            atb(new ReducePowerAction(p, p, Weight.POWER_ID, secondMagic));
    }

    private static class Power extends AbstractWielderPower implements OnReceivePowerPower {
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(DropAnchor.ID);

        @Override
        public boolean onReceivePower(AbstractPower p, AbstractCreature target, AbstractCreature source) {
            if (target == owner && p.ID == Weight.POWER_ID)
                return false;
            return true;
        }

        public Power(AbstractCreature owner, int amount) {
            super(DropAnchor.ID, powerStrings.NAME, PowerType.BUFF, true, owner, amount);
        }

        @Override
        public void atEndOfRound() {
            addToBot(new ReducePowerAction(owner, this.owner, ID, 1));
        }

        @Override
        public void updateDescription() {
            String[] d = powerStrings.DESCRIPTIONS;
            description = d[0] + amount + d[amount == 1 ? 1 : 2];
        }
    }
}