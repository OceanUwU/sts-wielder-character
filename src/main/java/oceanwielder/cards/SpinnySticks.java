package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import oceanwielder.wieldables.weapons.Emeici;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class SpinnySticks extends AbstractWielderCard {
    public final static String ID = makeID("SpinnySticks");

    public SpinnySticks() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(0, +2);
        weapon = new Emeici();
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        wield(weapon);
        if (magicNumber > 0) {
            applyToSelf(new StrengthPower(p, magicNumber));
            applyToSelf(new LoseStrengthPower(p, magicNumber));
        }
    }
}