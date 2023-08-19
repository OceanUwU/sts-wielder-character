package oceanwielder.cards.cardvars;

import static oceanwielder.WielderMod.makeID;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanwielder.cards.AbstractWielderCard;

public class SecondMagicNumber extends DynamicVariable {
    @Override
    public String key() {
        return makeID("m2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).isSecondMagicModified;
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).secondMagic;
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractWielderCard)
            ((AbstractWielderCard) card).isSecondMagicModified = v;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).baseSecondMagic;
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).upgradedSecondMagic;
        return false;
    }
}