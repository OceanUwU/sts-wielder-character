package oceanwielder.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.WielderMod;
import oceanwielder.util.Stamps;
import oceanwielder.wieldables.weapons.Crossbow;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class TakeAim extends AbstractWielderCard {
    public final static String ID = makeID("TakeAim");

    public TakeAim() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        weapon = new Crossbow();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard thisCard = this;
        wield(weapon);
        if (upgraded && WielderMod.weaponSlot.wieldable instanceof Crossbow)
            actB(() -> {
                Stamps.stamp(thisCard);
                for (AbstractCard c : p.hand.group) Stamps.stamp(c);
                for (AbstractCard c : p.drawPile.group) Stamps.stamp(c);
                for (AbstractCard c : p.discardPile.group) Stamps.stamp(c);
                for (AbstractCard c : p.exhaustPile.group) Stamps.stamp(c);
            });
    }
}