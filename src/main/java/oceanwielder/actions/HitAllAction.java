package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanwielder.WielderMod;
import oceanwielder.cards.Overextend;

import static oceanwielder.util.Wiz.*;

public class HitAllAction extends AbstractGameAction {
    private final AbstractCard fromCard;

    public HitAllAction(int hits, AbstractCard fromCard) {
        amount = hits;
        this.fromCard = fromCard;
    }

    public HitAllAction(AbstractCard fromCard) {
        this(1, fromCard);
    }

    public void update() {
        isDone = true;
        if (fromCard != null) {
            if (adp().hasPower(Overextend.ID))
                return;
        }
        for (int i = 0; i < amount; i++)
            WielderMod.weaponSlot.wieldable.useOnAll(fromCard);
    }
}