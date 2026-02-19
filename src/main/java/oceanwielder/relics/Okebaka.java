package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import oceanwielder.actions.GuardAction;
import oceanwielder.characters.TheWielder;
import oceanwielder.powers.AegisPower;

public class Okebaka extends AbstractWielderRelic {
    public static final String ID = makeID("Okebaka");
    public static final int POWER = 8;

    public Okebaka() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }

    @Override
    public void onGuard(AbstractCard c, AbstractMonster m, boolean fromCard, GuardAction action) {
        if (grayscale) return;
        flash();
        atb(new RelicAboveCreatureAction(adp(), this));
        applyToSelf(new AegisPower(adp(), POWER));
        grayscale = true;
        pulse = false;
    }

    @Override
    public void atBattleStart() {
        pulse = !grayscale;
        if (pulse) beginPulse();
    }

    @Override
    public void onVictory() {
        pulse = false;
        grayscale = false;
    }

    public void justEnteredRoom(AbstractRoom room) {
        grayscale = false;
        pulse = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }
}