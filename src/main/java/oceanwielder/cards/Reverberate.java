package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.powers.HitUpPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Reverberate extends AbstractWielderCard {
    public final static String ID = makeID("Reverberate");

    public Reverberate() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1, +1);
        setSecondMagic(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HitUpPower(p, magicNumber));
        bearWeight();
    }
}