package oceanwielder.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class ShovelKnightCard extends AbstractWielderCard {
    public final static String ID = makeID("ShovelKnightCard");

    public ShovelKnightCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setHits(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        boolean upped = upgraded;
        atb(new SelectCardsInHandAction(1, exDesc[0], upped, upped, c -> true, cards -> {
            if (cards.size() < 1) return;
            if (cards.get(0).type.equals(CardType.ATTACK) && !upped)
                hitTop(m, 1);
            att(new ExhaustSpecificCardAction(cards.get(0), p.hand));
        }));
    }
}