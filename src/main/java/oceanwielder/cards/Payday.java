package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.GainTixAction;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Payday extends AbstractWielderCard {
    public final static String ID = makeID(Payday.class.getSimpleName());

    public Payday() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        setMagic(2, +1);
    }

    @Override
    public void triggerWhenDrawn() {
        att(new GainTixAction(magicNumber), new ExhaustSpecificCardAction(this, adp().hand));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
       return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}
}