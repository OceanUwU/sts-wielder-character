package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Launder extends AbstractWielderCard {
    public final static String ID = makeID(Launder.class.getSimpleName());

    public Launder() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setMagic(2, +1);
        setSecondMagic(1);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        getTix(magicNumber);
        bearWeight();
    }
}