package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;

public class LightJab extends AbstractWielderCard {
    public final static String ID = makeID(LightJab.class.getSimpleName());

    public LightJab() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        tags.add(Jab);
        setHits(1);
        setMagic(0, +2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        gainVigor(secondMagic);
    }
}