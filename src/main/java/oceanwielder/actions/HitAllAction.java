package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import oceanwielder.WielderMod;
import oceanwielder.cards.Overextend;

import static oceanwielder.util.Wiz.*;

public class HitAllAction extends AbstractGameAction {
    private final boolean fromCard;

    public HitAllAction(int hits, boolean fromCard) {
        amount = hits;
        this.fromCard = fromCard;
    }

    public HitAllAction(boolean fromCard) {
        this(1, fromCard);
    }

    public void update() {
        isDone = true;
        if (fromCard) {
            if (adp().hasPower(Overextend.ID))
                return;
        }
        for (int i = 0; i < amount; i++)
            WielderMod.weaponSlot.wieldable.useOnAll();
    }
}