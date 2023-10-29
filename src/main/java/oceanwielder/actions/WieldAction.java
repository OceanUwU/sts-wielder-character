package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import oceanwielder.WielderMod;
import oceanwielder.wieldables.AbstractWieldable;
import oceanwielder.wieldables.shields.AbstractShield;
import oceanwielder.wieldables.weapons.AbstractWeapon;

public class WieldAction extends AbstractGameAction {
    AbstractWieldable wieldable;

    public WieldAction(AbstractWieldable wieldable) {
        this.wieldable = wieldable;
    }

    public void update() {
        isDone = true;
        if (wieldable instanceof AbstractWeapon)
            WielderMod.weaponSlot.wield(wieldable);
        else if (wieldable instanceof AbstractShield)
            WielderMod.shieldSlot.wield(wieldable);
        WielderMod.weaponSlot.stopPreviewing();
        WielderMod.shieldSlot.stopPreviewing();
    }
}