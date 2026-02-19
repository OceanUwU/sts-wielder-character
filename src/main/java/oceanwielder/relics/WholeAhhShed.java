package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import oceanwielder.actions.WieldAction;
import oceanwielder.characters.TheWielder;
import oceanwielder.wieldables.weapons.Chainsaw;

public class WholeAhhShed extends AbstractWielderRelic {
    public static final String ID = makeID("WholeAhhShed");
    public static final int POWER = 5;

    public WholeAhhShed() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }

    public void onEquip() {
        this.counter = 0;
    }

    public void atTurnStart() {
        if (counter == -1)
            counter += 2;
        else
            ++counter;

        if (counter == POWER) {
            counter = 0;
            flash();
            atb(new RelicAboveCreatureAction(adp(), this));
            atb(new WieldAction(new Chainsaw()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }
}
