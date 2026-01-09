package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.powers.Weight;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Bulwark extends AbstractWielderCard {
    public final static String ID = makeID(Bulwark.class.getSimpleName());

    public Bulwark() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setGuards(0, 1);
    }

    public void applyPowers() {
        super.applyPowers();
        int count = pwrAmt(adp(), Weight.POWER_ID);

        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + count + cardStrings.EXTENDED_DESCRIPTION[1];

        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        actB(() -> guardTop(pwrAmt(p, Weight.POWER_ID)));
    }
}