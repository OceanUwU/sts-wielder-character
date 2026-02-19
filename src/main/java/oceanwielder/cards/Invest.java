package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Invest extends AbstractWielderCard {
    public final static String ID = makeID(Invest.class.getSimpleName());

    public Invest() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(1);
        setSecondMagic(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        getTix(magicNumber);
        atb(new InkSpill.SelectCardsAction(p.discardPile.group, secondMagic, exDesc[0], upgraded, c -> true, cards -> {
            cards.forEach(c -> {
               p.discardPile.removeCard(c);
               p.hand.moveToDeck(c, false);
            });
            p.hand.refreshHandLayout();
        }));
    }
}