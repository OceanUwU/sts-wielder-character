package oceanwielder.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class PaperTrail extends AbstractWielderCard {
    public final static String ID = makeID(PaperTrail.class.getSimpleName());

    public PaperTrail() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setGuards(2, +1);
    }

    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && Stamps.isStamped((AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)))) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && Stamps.isStamped((AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2))))
                guardTop();
        });
    }
}