package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.powers.Weight;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Slam extends AbstractWielderCard {
    public final static String ID = makeID(Slam.class.getSimpleName());

    public Slam() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setHits(0, 1);
    }

    public void applyPowers() {
        super.applyPowers();
        int count = pwrAmt(adp(), Weight.POWER_ID);

        baseDesc = cardStrings.DESCRIPTION + exDesc[0] + count + exDesc[count == 1 ? 1 : 2];

        initializeDescription();
    }

    public void onMoveToDiscard() {
        baseDesc = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        hitTop(m, pwrAmt(p, Weight.POWER_ID));
    }
}