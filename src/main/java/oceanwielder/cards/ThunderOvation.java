package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class ThunderOvation extends AbstractWielderCard {
    public final static String ID = makeID(ThunderOvation.class.getSimpleName());

    public ThunderOvation() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setHits(2);
        setMagic(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hitAll();
        forAllMonstersLiving(mo -> applyToEnemy(mo, new VulnerablePower(mo, magicNumber, false)));
    }
}