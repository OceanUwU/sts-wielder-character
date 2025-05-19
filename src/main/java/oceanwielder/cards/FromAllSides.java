package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;

public class FromAllSides extends AbstractWielderCard {
    public final static String ID = makeID(FromAllSides.class.getSimpleName());

    public FromAllSides() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setHits(3, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
    }
}