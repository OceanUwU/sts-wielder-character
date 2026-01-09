package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.GainTixAction;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class CashOut extends AbstractWielderCard {
    public final static String ID = makeID(CashOut.class.getSimpleName());

    public CashOut() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setHits(2, +1);
        setMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hitAll();
        actB(() -> {
            int count = p.hand.size();
            if (count <= 0) return;
            att(new DiscardAction(p, p, count, true), new GainTixAction(magicNumber * count));
        });
    }
}