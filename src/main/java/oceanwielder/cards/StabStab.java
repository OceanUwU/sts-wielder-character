package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;

public class StabStab extends AbstractWielderCard {
    public final static String ID = makeID(StabStab.class.getSimpleName());

    public StabStab() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        tags.add(Stab);
        setHits(2);
        setMagic(0, +2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        gainVigor(magicNumber);
    }
}