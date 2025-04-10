package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.powers.GuardUpPower;
import oceanwielder.powers.HitUpPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class DoubleUp extends AbstractWielderCard {
    public final static String ID = makeID("DoubleUp");

    public DoubleUp() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setHits(1);
        setMagic(1, +1);
        setSecondMagic(0, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        applyToSelf(new GuardUpPower(p, magicNumber));
    }
}