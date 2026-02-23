package oceanwielder.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Collections;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Dismiss extends AbstractWielderCard {
    public final static String ID = makeID(Dismiss.class.getSimpleName());

    public Dismiss() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setGuards(1);
        setMagic(2, +2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        actB(() -> {
            ArrayList<AbstractCard> group = (ArrayList<AbstractCard>)p.drawPile.group.clone();
            Collections.sort(group);
            att(new SelectCardsAction(group, magicNumber, exDesc[0], true, c -> true, cards -> cards.stream().forEach(c -> att(new DiscardSpecificCardAction(c, p.drawPile)))));
        });
    }
}