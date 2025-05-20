package oceanwielder.actions;

import static oceanwielder.util.Wiz.adp;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.WielderMod;
import oceanwielder.cards.Overextend;

public class HitAction extends AbstractGameAction {
    private AbstractMonster target;
    private final AbstractCard fromCard;

    public HitAction(AbstractMonster m, int hits, AbstractCard fromCard) {
        target = m;
        amount = hits;
        this.fromCard = fromCard;
    }

    public HitAction(AbstractMonster m, AbstractCard fromCard) {
        this(m, 1, fromCard);
    }

    public void update() {
        isDone = true;
        if (amount <= 0) return;
        if (fromCard != null) {
            if (adp().hasPower(Overextend.ID))
                return;
        }
        for (int i = 0; i < amount; i++)
            WielderMod.weaponSlot.wieldable.use(fromCard, target);
    }
}