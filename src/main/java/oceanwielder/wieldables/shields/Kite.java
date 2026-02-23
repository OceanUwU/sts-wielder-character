package oceanwielder.wieldables.shields;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Kite extends AbstractShield {
    public static String ID = makeID("Kite");

    public Kite() {
        super(ID, 6, 2, 2);
    }

    @Override
    public void use(AbstractCard c, AbstractMonster m) {
        applyToSelfTop(new VigorPower(adp(), secondary));
        super.use(c, m);
    }

    public void dequipEffect() {
        applyToSelfTop(new StrengthPower(adp(), dequipPower));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        description += strings.DESCRIPTION[0] + secondary + strings.DESCRIPTION[1] + dequipPower + strings.DESCRIPTION[2];
    }
}