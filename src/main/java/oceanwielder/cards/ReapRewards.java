package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class ReapRewards extends AbstractWielderCard {
    public final static String ID = makeID(ReapRewards.class.getSimpleName());

    public ReapRewards() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, +1);
        setSecondMagic(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        getTix(magicNumber);
        gainVigor(secondMagic);
    }
}