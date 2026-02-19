package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.GainTixAction;
import oceanwielder.actions.GuardAction;
import oceanwielder.actions.HitAction;
import oceanwielder.actions.HitAllAction;
import oceanwielder.characters.TheWielder;

public class PunchCard extends AbstractWielderRelic {
    public static final String ID = makeID("PunchCard");
    public static final int POWER = 8;

    public PunchCard() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
        counter = 0;
    }

    @Override
    public void onHit(AbstractCard c, AbstractMonster m, boolean fromRealCard, HitAction action) {
        tick();
    }

    @Override
    public void onHitAll(AbstractCard c, boolean fromRealCard, HitAllAction action) {
        tick();
    }

    @Override
    public void onGuard(AbstractCard c, AbstractMonster m, boolean fromRealCard, GuardAction action) {
        tick();
    }

    private void tick() {
        if (++counter < POWER) return;
        counter = 0;
        flash();
        atb(new RelicAboveCreatureAction(adp(), this));
        atb(new GainTixAction(1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }
}
