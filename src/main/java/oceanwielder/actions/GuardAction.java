package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import oceanwielder.WielderMod;
import oceanwielder.cards.Bide;
import oceanwielder.cards.Overextend;
import oceanwielder.powers.AbstractWielderPower;

import static oceanwielder.util.Wiz.*;

public class GuardAction extends AbstractGameAction {
    private AbstractMonster target;
    private final AbstractCard card;
    private final boolean fromRealCard;
    public int additionalNonReal = 0;

    public GuardAction(int guards, AbstractCard fromCard, boolean fromRealCard) {
        amount = guards;
        this.card = fromCard;
        this.fromRealCard = fromRealCard;
    }

    public GuardAction(AbstractCard fromCard, boolean fromRealCard) {
        this(1, fromCard, fromRealCard);
    }

    public void update() {
        isDone = true;
        if (card != null) {
            if (adp().hasPower(Overextend.ID) || adp().hasPower(Bide.ID))
                return;
        }
        do {
            boolean real = fromRealCard && additionalNonReal <= 0;
            if (additionalNonReal > 0) additionalNonReal--;
            for (AbstractPower p : adp().powers)
                if (p instanceof AbstractWielderPower)
                    ((AbstractWielderPower)p).onGuard(card, target, real, this);
            WielderMod.shieldSlot.wieldable.use(card, target);
        } while (--amount > 0);
    }
}