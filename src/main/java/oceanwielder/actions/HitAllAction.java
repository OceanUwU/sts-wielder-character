package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import oceanwielder.WielderMod;

public class HitAllAction extends AbstractGameAction {
    public HitAllAction(int hits) {
        amount = hits;
    }

    public HitAllAction() {
        this(1);
    }

    public void update() {
        isDone = true;
        for (int i = 0; i < amount; i++)
            WielderMod.weaponSlot.wieldable.useOnAll();
    }
}