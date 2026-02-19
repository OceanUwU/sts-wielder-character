package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanwielder.actions.GuardAction;
import oceanwielder.actions.HitAction;
import oceanwielder.actions.HitAllAction;
import oceanwielder.characters.TheWielder;
import oceanwielder.powers.LambdaPower;

public class RemarkablyThrowableRock extends AbstractWielderRelic {
    public static final String ID = makeID("RemarkablyThrowableRock");

    public RemarkablyThrowableRock() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        applyToSelfTop(new LambdaPower(ID + "Right", DESCRIPTIONS, DESCRIPTIONS[5], PowerType.BUFF, false, adp(), 1) {
            private boolean triggered = false;

            @Override
            public void onGuard(AbstractCard c, AbstractMonster m, boolean fromCard, GuardAction action) {
                if (triggered || !fromCard) return;
                triggered = true;
                flash();
                action.amount += amount;
                action.additionalNonReal += amount;
                atb(new RemoveSpecificPowerAction(owner, owner, this));
            }

            @Override
            public void updateDescription() {
                if (amount == 1)
                    description = strings[6];
                else
                    description = strings[7] + amount + strings[8];
            }
        });
        applyToSelfTop(new LambdaPower(ID + "Left", DESCRIPTIONS, DESCRIPTIONS[1], PowerType.BUFF, false, adp(), 1) {
            private boolean triggered = false;

            @Override
            public void onHit(AbstractCard c, AbstractMonster m, boolean fromCard, HitAction action) {
                if (triggered || !fromCard) return;
                triggered = true;
                flash();
                action.amount += amount;
                action.additionalNonReal += amount;
                atb(new RemoveSpecificPowerAction(owner, owner, this));
            }

            @Override
            public void onHitAll(AbstractCard c, boolean fromCard, HitAllAction action) {
                if (triggered || !fromCard) return;
                triggered = true;
                flash();
                action.amount += amount;
                action.additionalNonReal += amount;
                atb(new RemoveSpecificPowerAction(owner, owner, this));
            }

            @Override
            public void updateDescription() {
                if (amount == 1)
                    description = strings[2];
                else
                    description = strings[3] + amount + strings[4];
            }
        });
        att(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
}
