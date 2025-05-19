package oceanwielder.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class ExpressDelivery extends AbstractWielderCard {
    public final static String ID = makeID(ExpressDelivery.class.getSimpleName());

    public ExpressDelivery() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setHits(1);
        setMagic(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hitRandom();
        final int stamps = magicNumber;
        actB(() -> {
            int s = stamps;
            ArrayList<AbstractCard> cards = new ArrayList<>(adp().hand.group.stream().filter(c -> Stamps.canStamp(c)).collect(Collectors.toList()));
            Collections.shuffle(cards, new java.util.Random(AbstractDungeon.cardRandomRng.randomLong()));
            while (cards.size() > 0 && s-- > 0)
                Stamps.stamp(cards.remove(0));
        });
    }
}