package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.WielderMod;

public class HitAction extends AbstractGameAction {
    private int hits;
    private AbstractMonster target;

    public HitAction(AbstractMonster m, int hits) {
        target = m;
        this.hits = hits;
    }

    public HitAction(AbstractMonster m) {
        this(m, 1);
    }

    public void update() {
        isDone = true;
        WielderMod.weaponSlot.wieldable.fontScale *= 2f;
        for (int i = 0; i < hits; i++)
            WielderMod.weaponSlot.wieldable.use(target);
    }
}