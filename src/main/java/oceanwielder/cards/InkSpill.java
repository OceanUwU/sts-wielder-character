package oceanwielder.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class InkSpill extends AbstractWielderCard {
    public final static String ID = makeID(InkSpill.class.getSimpleName());

    public InkSpill() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.NONE);
        setHits(1);
        setMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        atb(new SelectCardsAction(p.drawPile.group, cardStrings.EXTENDED_DESCRIPTION[0], cards -> {
            cards.forEach(c -> Stamps.stamp(c));
        }));
    }
}