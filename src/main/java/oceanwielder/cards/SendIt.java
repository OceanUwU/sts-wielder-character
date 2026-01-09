package oceanwielder.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class SendIt extends AbstractWielderCard {
    public final static String ID = makeID(SendIt.class.getSimpleName());

    public SendIt() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(1, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new MultiGroupSelectAction(exDesc[0], (cards, groups) -> {
            cards.forEach(c -> atb(new NewQueueCardAction(c, true, false, true)));
        }, 1, c -> Stamps.isStamped(c), CardGroup.CardGroupType.DRAW_PILE, CardGroup.CardGroupType.DISCARD_PILE));
    }
}