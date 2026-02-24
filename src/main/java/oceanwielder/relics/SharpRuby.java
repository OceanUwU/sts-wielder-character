package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.Orichalcum;
import oceanwielder.actions.GuardAction;
import oceanwielder.characters.TheWielder;

public class SharpRuby extends AbstractWielderRelic {
    public static final String ID = makeID("SharpRuby");
    public static final int POWER = 2;

    public SharpRuby() {
        super(ID, RelicTier.RARE, LandingSound.CLINK, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
        counter = 0;
    }

    public void onEquip() {
        counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (counter == POWER - 1)
            beginLongPulse();
    }

    public void onPlayerEndTurn() {
        if (counter == -1)
            counter += 2;
        else
            ++counter;

        if (counter == POWER) {
            counter = 0;
            flash();

            atb(new RelicAboveCreatureAction(adp(), this));
            atb(new GuardAction(null, false));
        }
        stopPulse();
    }

    public void onVictory() {
        stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }
}
