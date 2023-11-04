package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.WielderMod;
import oceanwielder.wieldables.weapons.Hammer;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class HeavenlyWrath extends AbstractWielderCard {
    public final static String ID = makeID("HeavenlyWrath");

    public HeavenlyWrath() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setHits(1);
        setMagic(0, +4);
        weapon = new Hammer();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        wield(weapon);
        actB(() -> {
            WielderMod.weaponSlot.wieldable.baseDequipPower += magicNumber;
            WielderMod.weaponSlot.wieldable.applyPowers();
        });
    }
}