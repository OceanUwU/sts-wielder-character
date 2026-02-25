package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import oceanwielder.WielderMod;
import oceanwielder.cards.Bide;
import oceanwielder.cards.Overextend;
import oceanwielder.powers.AbstractWielderPower;
import oceanwielder.relics.AbstractWielderRelic;

import static oceanwielder.util.Wiz.*;

public class GuardAction extends AbstractGameAction {
    private AbstractMonster target;
    private final AbstractCard card;
    private final boolean notARepeat;
    public int additionalNonReal = 0;

    public GuardAction(int guards, AbstractCard fromCard, boolean notARepeat) {
        amount = guards;
        this.card = fromCard;
        this.notARepeat = notARepeat;
    }

    public GuardAction(AbstractCard fromCard, boolean notARepeat) {
        this(1, fromCard, notARepeat);
    }

    public void update() {
        isDone = true;
        if (amount <= 0) return;
        if (card != null && (adp().hasPower(Overextend.ID) || adp().hasPower(Bide.ID)))
            return;
        do {
            boolean real = notARepeat && additionalNonReal <= 0;
            if (additionalNonReal > 0) additionalNonReal--;
            for (AbstractPower p : adp().powers)
                if (p instanceof AbstractWielderPower)
                    ((AbstractWielderPower)p).onGuard(card, target, real, this);
            for (AbstractRelic r : adp().relics)
                if (r instanceof AbstractWielderRelic)
                    ((AbstractWielderRelic)r).onGuard(card, target, real, this);
            WielderMod.shieldSlot.wieldable.use(card, target);
        } while (--amount > 0);
    }
}