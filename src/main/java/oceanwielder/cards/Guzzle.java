package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;

public class Guzzle extends AbstractWielderCard {
    public final static String ID = makeID(Guzzle.class.getSimpleName());

    public Guzzle() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.NONE);
        setExhaust(true);
        setRetain(false, true);
        Stamps.stamp(this, Stamps.MoxiePower.MOXIE_PER_ENERGY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}
}