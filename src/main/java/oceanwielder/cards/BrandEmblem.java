package oceanwielder.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class BrandEmblem extends AbstractWielderCard {
    public final static String ID = makeID(BrandEmblem.class.getSimpleName());

    public BrandEmblem() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setCostUpgrade(-1);
        setHits(1);
        setMagic(1);
        setExhaust(true);
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            ArrayList<AbstractCard> possibleCards = (ArrayList<AbstractCard>)AbstractDungeon.player.masterDeck.group.clone();
            possibleCards.removeIf(c -> !Stamps.canStamp(c));
            if (possibleCards.isEmpty()) return;
            AbstractCard c = (AbstractCard)possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
            AbstractCard copy = c.makeStatEquivalentCopy();
            Stamps.stamp(c);
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            Stamps.stamp(copy);
        }); 
        hit(m);
    }
}