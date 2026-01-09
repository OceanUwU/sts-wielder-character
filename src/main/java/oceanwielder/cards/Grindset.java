package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Grindset extends AbstractWielderCard {
    public final static String ID = makeID(Grindset.class.getSimpleName());

    public Grindset() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setHits(1);
        setMagic(1, +1);
        shuffleBackIntoDrawPile = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        atb(new Stamps.Action(this, magicNumber));
    }
}