package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.WielderMod;

public class GuardAction extends AbstractGameAction {
    private int guards;
    private AbstractMonster target;

    public GuardAction(int guards) {
        this.guards = guards;
    }

    public GuardAction() {
        this(1);
    }

    public void update() {
        isDone = true;
        WielderMod.shieldSlot.wieldable.fontScale *= 2f;
        for (int i = 0; i < guards; i++)
            WielderMod.shieldSlot.wieldable.use(target);
    }
}