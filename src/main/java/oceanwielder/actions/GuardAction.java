package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import oceanwielder.WielderMod;
import oceanwielder.cards.Bide;
import oceanwielder.cards.Overextend;
import oceanwielder.powers.AbstractWielderPower;

import static oceanwielder.util.Wiz.*;

public class GuardAction extends AbstractGameAction {
    private int guards;
    private AbstractMonster target;
    private final AbstractCard fromCard;
    private final boolean fromRealCard;

    public GuardAction(int guards, AbstractCard fromCard, boolean fromRealCard) {
        this.guards = guards;
        this.fromCard = fromCard;
        this.fromRealCard = fromRealCard;
    }

    public GuardAction(AbstractCard fromCard, boolean fromRealCard) {
        this(1, fromCard, fromRealCard);
    }

    public void update() {
        isDone = true;
        if (fromCard != null) {
            if (adp().hasPower(Overextend.ID) || adp().hasPower(Bide.ID))
                return;
        }
        for (int i = 0; i < guards; i++) {
            for (AbstractPower p : adp().powers)
                if (p instanceof AbstractWielderPower)
                    ((AbstractWielderPower)p).onGuard(fromCard, target, fromRealCard);
            WielderMod.shieldSlot.wieldable.use(fromCard, target);
        }
    }
}