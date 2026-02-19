package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import oceanwielder.WielderMod;
import oceanwielder.cards.Overextend;
import oceanwielder.powers.AbstractWielderPower;
import oceanwielder.relics.AbstractWielderRelic;

import static oceanwielder.util.Wiz.*;

public class HitAllAction extends AbstractGameAction {
    private final AbstractCard card;
    private final boolean fromRealCard;
    public int additionalNonReal = 0;

    public HitAllAction(int hits, AbstractCard fromCard, boolean fromRealCard) {
        amount = hits;
        this.card = fromCard;
        this.fromRealCard = fromRealCard;
    }

    public HitAllAction(AbstractCard fromCard, boolean fromRealCard) {
        this(1, fromCard, fromRealCard);
    }

    public void update() {
        isDone = true;
        if (card != null) {
            if (adp().hasPower(Overextend.ID))
                return;
        }
        do {
            boolean real = fromRealCard && additionalNonReal <= 0;
            if (additionalNonReal > 0) additionalNonReal--;
            for (AbstractPower p : adp().powers)
                if (p instanceof AbstractWielderPower)
                    ((AbstractWielderPower)p).onHitAll(card, real, this);
            for (AbstractRelic r : adp().relics)
                if (r instanceof AbstractWielderRelic)
                    ((AbstractWielderRelic)r).onHitAll(card, real, this);
            WielderMod.weaponSlot.wieldable.useOnAll(card);
        } while (--amount > 0);
    }
}