package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class CinchUp extends AbstractWielderCard {
    public final static String ID = makeID(CinchUp.class.getSimpleName());

    public CinchUp() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setGuards(1);
        setMagic(1, +1);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        applyToSelf(new StrengthPower(p, magicNumber));
        bearWeight();
    }
}