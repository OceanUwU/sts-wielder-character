package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import oceanwielder.wieldables.shields.Heater;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class ExpandDefenses extends AbstractWielderCard {
    public final static String ID = makeID("ExpandDefenses");

    public ExpandDefenses() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, +2);
        shield = new Heater();
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        wield(shield);
        if (magicNumber > 0) {
            applyToSelf(new DexterityPower(p, magicNumber));
            applyToSelf(new LoseDexterityPower(p, magicNumber));
        }
    }
}