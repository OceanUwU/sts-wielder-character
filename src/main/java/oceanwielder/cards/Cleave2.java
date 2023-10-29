package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Cleave2 extends AbstractWielderCard {
    public final static String ID = makeID("Cleave2");

    public Cleave2() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        setHits(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hitAll();
    }
}