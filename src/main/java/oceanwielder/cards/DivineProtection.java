package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.EasyXCostAction;
import oceanwielder.powers.AegisPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class DivineProtection extends AbstractWielderCard {
    public final static String ID = makeID(DivineProtection.class.getSimpleName());

    public DivineProtection() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(8);
        setSecondMagic(0, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (power, params) -> {
            applyToSelfTop(new AegisPower(adp(), magicNumber * power));
            return true;
        }));
        if (secondMagic > 0)
            atb(new GainEnergyAction(secondMagic));
    }
}