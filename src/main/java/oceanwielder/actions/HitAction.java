package oceanwielder.actions;

import static oceanwielder.util.Wiz.adp;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import oceanwielder.WielderMod;
import oceanwielder.cards.Overextend;
import oceanwielder.powers.AbstractWielderPower;
import oceanwielder.relics.AbstractWielderRelic;

public class HitAction extends AbstractGameAction {
    private AbstractMonster target;
    private final AbstractCard card;
    private final boolean notARepeat;
    public int additionalNonReal = 0;

    public HitAction(AbstractMonster m, int hits, AbstractCard fromCard, boolean notARepeat) {
        target = m;
        amount = hits;
        this.card = fromCard;
        this.notARepeat = notARepeat;
    }

    public HitAction(AbstractMonster m, AbstractCard fromCard, boolean notARepeat) {
        this(m, 1, fromCard, notARepeat);
    }

    public void update() {
        isDone = true;
        if (amount <= 0) return;
        if (card != null) {
            if (adp().hasPower(Overextend.ID))
                return;
        }
        do {
            boolean real = notARepeat && additionalNonReal <= 0;
            if (additionalNonReal > 0) additionalNonReal--;
            for (AbstractPower p : adp().powers)
                if (p instanceof AbstractWielderPower)
                    ((AbstractWielderPower)p).onHit(card, target, real, this);
            for (AbstractRelic r : adp().relics)
                if (r instanceof AbstractWielderRelic)
                    ((AbstractWielderRelic)r).onHit(card, target, real, this);
            WielderMod.weaponSlot.wieldable.use(card, target);
        } while (--amount > 0);
    }
}