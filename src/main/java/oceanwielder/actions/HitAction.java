package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        if (amount <= 0) return;
        if (target == null)
            target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        for (int i = 0; i < amount; i++)
            WielderMod.weaponSlot.wieldable.use(target);
    }
}