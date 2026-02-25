package oceanwielder.wieldables.shields;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import oceanwielder.actions.GainTixAction;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Hologram extends AbstractShield {
    public static String ID = makeID("Hologram");

    public Hologram() {
        super(ID, 4, 3, 2);
    }

    @Override
    public void use(AbstractCard c, AbstractMonster m) {
        for (int i = 0; i < secondaryTimes; i++)
        applyToSelfTop(new NextTurnBlockPower(adp(), secondary));
        super.use(c, m);
    }

    @Override
    public void applyPowers(AbstractCard c) {
        super.applyPowers(c);
        if (c == null) c = sim;
        int base = c.baseBlock;
        c.baseBlock += baseSecondary;
        c.applyPowers();
        c.baseBlock = base;
        secondary = Math.max(c.block, 0);
        secondaryTimes = primaryTimes;
        updateDescription();
    }

    public void dequipEffect() {
        if (dequipPower > 0)
            att(new GainTixAction(dequipPower));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if (secondaryTimes == 1)
            description += strings.DESCRIPTION[0] + secondary + strings.DESCRIPTION[1] + dequipPower + strings.DESCRIPTION[4];
        else
            description += strings.DESCRIPTION[0] + secondary + strings.DESCRIPTION[2] + secondaryTimes + strings.DESCRIPTION[3] + dequipPower + strings.DESCRIPTION[4];
    }
}