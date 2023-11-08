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
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setGuards(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean upped = upgraded;
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], cards -> {
            if (cards.get(0).type.equals(CardType.ATTACK) || upped)
                guardTop();
            att(new ExhaustSpecificCardAction(cards.get(0), p.hand));
        }));
    }
}