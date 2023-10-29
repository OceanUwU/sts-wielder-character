package oceanwielder.wieldables.weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Cutlass extends AbstractWeapon {
    public static String ID = makeID("Cutlass");

    public Cutlass() {
        super(ID, 6, -1, 8);
    }

    public void dequipEffect() {
        target = getRandomTarget();
        if (target != null)
            att(new DamageAction(target, new DamageInfo(adp(), dequipPower, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
    }
}