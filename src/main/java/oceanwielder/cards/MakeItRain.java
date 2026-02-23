package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.HitAction;
import oceanwielder.util.Tix;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class MakeItRain extends AbstractWielderCard {
    public final static String ID = makeID(MakeItRain.class.getSimpleName());

    public MakeItRain() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setRetain(false, true);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> att(new HitAction(m, Tix.spentThisTurn, this, true)));
    }

    public void applyPowers() {
        super.applyPowers();
        baseDesc = cardStrings.DESCRIPTION + exDesc[0] + Tix.spentThisTurn + exDesc[1];
        initializeDescription();
    }

    public void onMoveToDiscard() {
        baseDesc = cardStrings.DESCRIPTION;
        initializeDescription();
    }
}