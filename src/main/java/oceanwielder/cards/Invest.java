package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Invest extends AbstractWielderCard {
    public final static String ID = makeID(Invest.class.getSimpleName());

    public Invest() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        getTix(magicNumber);
        atb(new DiscardPileToTopOfDeckAction(p));
    }
}