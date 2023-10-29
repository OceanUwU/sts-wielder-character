package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Wax extends AbstractWielderCard {
    public final static String ID = makeID("Wax");

    public Wax() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setGuards(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        boolean upped = upgraded;
        actB(() -> adp().hand.group.forEach(c -> {
            att(new Stamps.Action(c));
            if (upped)
                att(new UpgradeSpecificCardAction(c));
        }));
    }
}