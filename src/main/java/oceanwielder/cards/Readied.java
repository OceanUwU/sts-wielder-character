package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import oceanwielder.actions.EasyXCostAction;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Readied extends AbstractWielderCard {
    public final static String ID = makeID(Readied.class.getSimpleName());

    public Readied() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(6);
        setSecondMagic(0, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (power, params) -> {
            if (power == 0) return true;
            applyToSelfTop(new VigorPower(adp(), magicNumber * power));
            return true;
        }));
        if (secondMagic > 0)
            atb(new GainEnergyAction(secondMagic));
    }
}