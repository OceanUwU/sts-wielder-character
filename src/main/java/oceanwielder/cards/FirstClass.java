package oceanwielder.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;

public class FirstClass extends AbstractWielderCard {
    public final static String ID = makeID(FirstClass.class.getSimpleName());

    public FirstClass() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setHits(3);
        setMagic(0, +3);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = exDesc[0];
        return super.canUse(p, m) && Stamps.isStamped(this);
    }

    public void triggerOnGlowCheck() {
        boolean glow = Stamps.isStamped(this);
        if (glow)
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        else
            glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        gainVigor(magicNumber);
    }
}