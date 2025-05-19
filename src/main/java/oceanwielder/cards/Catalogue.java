package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Catalogue extends AbstractWielderCard {
    public final static String ID = makeID(Catalogue.class.getSimpleName());

    public Catalogue() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setMagic(0, +2);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> p.hand.group.forEach(c -> Stamps.stamp(c)));
        gainAegis(magicNumber);
        bearWeight();
    }
}