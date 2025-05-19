package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class DetectFlaws extends AbstractWielderCard {
    public final static String ID = makeID(DetectFlaws.class.getSimpleName());

    public DetectFlaws() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        setMagic(2, +1);
        setSecondMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        gainVigor(secondMagic);
    }
}