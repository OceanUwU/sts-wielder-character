package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.DiscardFromDrawPile;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Dismiss extends AbstractWielderCard {
    public final static String ID = makeID(Dismiss.class.getSimpleName());

    public Dismiss() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setGuards(1);
        setMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        atb(new DiscardFromDrawPile(magicNumber));
    }
}