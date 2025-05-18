package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Twirl extends AbstractWielderCard {
    public final static String ID = makeID(Twirl.class.getSimpleName());

    public Twirl() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        setHits(1);
        setGuards(1);
        setSecondMagic(1);
        setCostUpgrade(-1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        hit(m);
        bearWeight();
    }
}