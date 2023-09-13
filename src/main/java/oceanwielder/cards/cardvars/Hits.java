package oceanwielder.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanwielder.cards.AbstractWielderCard;

import static oceanwielder.WielderMod.makeID;

public class Hits extends DynamicVariable {
    @Override
    public String key() {
        return makeID("h");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).isHitsModified;
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).hits;
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractWielderCard)
            ((AbstractWielderCard) card).isHitsModified = v;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).baseHits;
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).upgradedHits;
        return false;
    }
    
}
