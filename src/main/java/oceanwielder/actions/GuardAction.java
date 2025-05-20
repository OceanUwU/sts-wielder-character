package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.WielderMod;
import oceanwielder.cards.Overextend;

import static oceanwielder.util.Wiz.*;

public class GuardAction extends AbstractGameAction {
    private int guards;
    private AbstractMonster target;
    private final AbstractCard fromCard;

    public GuardAction(int guards, AbstractCard fromCard) {
        this.guards = guards;
        this.fromCard = fromCard;
    }

    public GuardAction(AbstractCard fromCard) {
        this(1, fromCard);
    }

    public void update() {
        isDone = true;
        if (fromCard != null) {
            if (adp().hasPower(Overextend.ID))
                return;
        }
        for (int i = 0; i < guards; i++)
            WielderMod.shieldSlot.wieldable.use(fromCard, target);
    }
}