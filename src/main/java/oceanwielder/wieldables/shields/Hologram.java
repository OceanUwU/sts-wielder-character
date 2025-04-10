package oceanwielder.wieldables.shields;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import oceanwielder.actions.GainTixAction;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Hologram extends AbstractShield {
    public static String ID = makeID("Hologram");

    public Hologram() {
        super(ID, 3, 3, 2);
    }

    @Override
    public void use(AbstractMonster m) {
        applyToSelfTop(new NextTurnBlockPower(adp(), secondary));
        super.use(m);
    }

    public void dequipEffect() {
        att(new GainTixAction(dequipPower));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        description += strings.DESCRIPTION[0] + secondary + strings.DESCRIPTION[1] + dequipPower + strings.DESCRIPTION[2];
    }
}