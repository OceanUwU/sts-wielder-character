package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class CloseUpShop extends AbstractWielderCard {
    public final static String ID = makeID(CloseUpShop.class.getSimpleName());

    public CloseUpShop() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setGuards(1);
        setMagic(1);
        setSecondMagic(0, +3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        getTix(magicNumber);
        gainAegis(secondMagic);
    }
}