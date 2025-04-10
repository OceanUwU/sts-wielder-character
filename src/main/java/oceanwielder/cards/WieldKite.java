package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.powers.Weight;
import oceanwielder.wieldables.shields.Kite;
import oceanwielder.wieldables.weapons.Chainsaw;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.applyToSelf;

public class WieldKite extends AbstractWielderCard {
    public final static String ID = makeID("WieldKite");

    public WieldKite() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        shield = new Kite();
        setMagic(2, -1);
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        wield(shield);
        applyToSelf(new Weight(p, magicNumber));
    }
}