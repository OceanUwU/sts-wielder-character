package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.wieldables.weapons.Scythe;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class SoulScan extends AbstractWielderCard {
    public final static String ID = makeID("SoulScan");

    public SoulScan() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setGuards(1);
        weapon = new Scythe();
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        wield(weapon);
    }
}