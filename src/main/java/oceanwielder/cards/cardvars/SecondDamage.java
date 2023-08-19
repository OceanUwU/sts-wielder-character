package oceanwielder.cards.cardvars;

import static oceanwielder.WielderMod.makeID;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanwielder.cards.AbstractWielderCard;

public class SecondDamage extends DynamicVariable {
    @Override
    public String key() {
        return makeID("sd");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).isSecondDamageModified;
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractWielderCard)
            ((AbstractWielderCard) card).isSecondDamageModified = v;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).secondDamage;
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).baseSecondDamage;
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractWielderCard)
            return ((AbstractWielderCard) card).upgradedSecondDamage;
        return false;
    }
}