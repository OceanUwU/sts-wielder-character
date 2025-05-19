package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.WielderMod;
import oceanwielder.wieldables.weapons.Crossbow;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class TakeAim extends AbstractWielderCard {
    public final static String ID = makeID("TakeAim");

    public TakeAim() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        weapon = new Crossbow();
        setMagic(0, +1);
        setSecondMagic(Crossbow.DAMAGE, +1);
        exhaust = true;
        initializeDescription();
    }

    @Override public void applyPowers() {
        baseSecondMagic = secondMagic = Crossbow.DAMAGE + magicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        wield(weapon);
        if (secondMagic != Crossbow.DAMAGE)
            actB(() -> {
                if (!(WielderMod.weaponSlot.wieldable instanceof Crossbow)) return;
                WielderMod.weaponSlot.wieldable.basePrimary = Crossbow.DAMAGE + magicNumber;
                WielderMod.weaponSlot.wieldable.applyPowers();
            });
    }
}