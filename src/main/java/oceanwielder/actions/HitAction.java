package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.WielderMod;

public class HitAction extends AbstractGameAction {
    private AbstractMonster target;

    public HitAction(AbstractMonster m, int hits) {
        target = m;
        amount = hits;
    }

    public HitAction(AbstractMonster m) {
        this(m, 1);
    }

    public void update() {
        isDone = true;
        for (int i = 0; i < amount; i++)
            WielderMod.weaponSlot.wieldable.use(target);
    }
}