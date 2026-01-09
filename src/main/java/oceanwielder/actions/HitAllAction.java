package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import oceanwielder.WielderMod;
import oceanwielder.cards.Overextend;
import oceanwielder.powers.AbstractWielderPower;

import static oceanwielder.util.Wiz.*;

public class HitAllAction extends AbstractGameAction {
    private final AbstractCard fromCard;
    private final boolean fromRealCard;

    public HitAllAction(int hits, AbstractCard fromCard, boolean fromRealCard) {
        amount = hits;
        this.fromCard = fromCard;
        this.fromRealCard = fromRealCard;
    }

    public HitAllAction(AbstractCard fromCard, boolean fromRealCard) {
        this(1, fromCard, fromRealCard);
    }

    public void update() {
        isDone = true;
        if (fromCard != null) {
            if (adp().hasPower(Overextend.ID))
                return;
        }
        for (int i = 0; i < amount; i++) {
            for (AbstractPower p : adp().powers)
                if (p instanceof AbstractWielderPower)
                    ((AbstractWielderPower)p).onHitAll(fromCard, fromRealCard);
            WielderMod.weaponSlot.wieldable.useOnAll(fromCard);
        }
    }
}