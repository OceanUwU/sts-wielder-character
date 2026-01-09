package oceanwielder.actions;

import static oceanwielder.util.Wiz.adp;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import oceanwielder.WielderMod;
import oceanwielder.cards.Overextend;
import oceanwielder.powers.AbstractWielderPower;

public class HitAction extends AbstractGameAction {
    private AbstractMonster target;
    private final AbstractCard fromCard;
    private final boolean fromRealCard;

    public HitAction(AbstractMonster m, int hits, AbstractCard fromCard, boolean fromRealCard) {
        target = m;
        amount = hits;
        this.fromCard = fromCard;
        this.fromRealCard = fromRealCard;
    }

    public HitAction(AbstractMonster m, AbstractCard fromCard, boolean fromRealCard) {
        this(m, 1, fromCard, fromRealCard);
    }

    public void update() {
        isDone = true;
        if (amount <= 0) return;
        if (fromCard != null) {
            if (adp().hasPower(Overextend.ID))
                return;
        }
        for (int i = 0; i < amount; i++) {
            for (AbstractPower p : adp().powers)
                if (p instanceof AbstractWielderPower)
                    ((AbstractWielderPower)p).onHit(fromCard, target, fromRealCard);
            WielderMod.weaponSlot.wieldable.use(fromCard, target);
        }
    }
}