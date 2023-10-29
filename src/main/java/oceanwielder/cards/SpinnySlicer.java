package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.wieldables.weapons.ThrowingStar;

import static oceanwielder.WielderMod.makeID;

public class SpinnySlicer extends AbstractWielderCard {
    public final static String ID = makeID("SpinnySlicer");

    public SpinnySlicer() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        setCostUpgrade(-1);
        weapon = new ThrowingStar();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        wield(weapon);
    }
}