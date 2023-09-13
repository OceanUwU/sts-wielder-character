package oceanwielder.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanwielder.cards.AbstractWielderCard;

import static oceanwielder.WielderMod.makeID;

public class Guards extends DynamicVariable {
    @Override
    public String key() {
        return makeID("g");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).isGuardsModified;
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).guards;
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractWielderCard)
            ((AbstractWielderCard) card).isGuardsModified = v;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).baseGuards;
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).upgradedGuards;
        return false;
    }
    
}
