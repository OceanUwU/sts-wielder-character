package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;

public class Fortify extends AbstractWielderCard {
    public final static String ID = makeID(Fortify.class.getSimpleName());

    public Fortify() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setGuards(3, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
    }
}