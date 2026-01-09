package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class OverAndOver extends AbstractWielderCard {
    public final static String ID = makeID(OverAndOver.class.getSimpleName());

    public OverAndOver() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setHits(0, 1);
    }

    public void applyPowers() {
        super.applyPowers();
        int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

        rawDescription = cardStrings.DESCRIPTION;
        rawDescription = rawDescription + exDesc[0] + count;
        if (count == 1) {
            rawDescription = rawDescription + exDesc[1];
        } else {
            rawDescription = rawDescription + exDesc[2];
        }

        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        actB(() -> hitTop(m, AbstractDungeon.actionManager.cardsPlayedThisTurn.size()));
    }
}