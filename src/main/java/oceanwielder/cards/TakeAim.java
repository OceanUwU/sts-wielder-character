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
        exhaust = true;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        wield(weapon);
        if (magicNumber > 0)
            actB(() -> {
                WielderMod.weaponSlot.wieldable.basePrimary += magicNumber;
                WielderMod.weaponSlot.wieldable.applyPowers();
            });
    }
}