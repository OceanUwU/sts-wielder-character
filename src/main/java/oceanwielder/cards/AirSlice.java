package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;

public class AirSlice extends AbstractWielderCard {
    public final static String ID = makeID(AirSlice.class.getSimpleName());

    public AirSlice() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        setHits(1);
        setMagic(2, +2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hitAll();
        gainVigor(magicNumber);
    }
}