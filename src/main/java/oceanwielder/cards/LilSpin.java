package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;

public class LilSpin extends AbstractWielderCard {
    public final static String ID = makeID(LilSpin.class.getSimpleName());

    public LilSpin() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setGuards(1);
        setMagic(0, +3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        gainAegis(magicNumber);
    }
}