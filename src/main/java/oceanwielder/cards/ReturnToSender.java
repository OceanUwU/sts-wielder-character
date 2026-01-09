package oceanwielder.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.Collections;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import basemod.BaseMod;

public class ReturnToSender extends AbstractWielderCard {
    public final static String ID = makeID(ReturnToSender.class.getSimpleName());

    public ReturnToSender() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.NONE);
        setHits(1);
        setMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        atb(new MultiGroupSelectAction(exDesc[0], (cards, groups) -> {
            Collections.reverse(cards);
            cards.forEach(c -> att(new AbstractGameAction() {
                public void update() {
                    isDone = true;
                    if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                        if (groups.get(c) == p.drawPile)
                            p.drawPile.moveToDiscardPile(c);
                        p.createHandIsFullDialog();
                    } else
                        p.hand.moveToHand(c, groups.get(c));
                }
            }));
        }, 1, c -> Stamps.isStamped(c), CardGroup.CardGroupType.DRAW_PILE, CardGroup.CardGroupType.DISCARD_PILE));
    }
}