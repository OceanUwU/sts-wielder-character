package oceanwielder.wieldables;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.wieldables.shields.AbstractShield;
import oceanwielder.wieldables.weapons.AbstractWeapon;

import static oceanwielder.WielderMod.makeID;

public class UnknownWieldable extends AbstractWieldable {
    public UnknownWieldable(boolean isWeapon) {
        super(makeID("Unknown"), -1, -1, -1, isWeapon ? AbstractWeapon.X_OFFSET : AbstractShield.X_OFFSET, isWeapon ? AbstractWeapon.Y_OFFSET : AbstractShield.Y_OFFSET);
    }

    public void use(AbstractMonster m) {}
    public void dequipEffect() {}

    @Override
    public void updateDescription() {
        description = "";
    }
}