package oceanwielder.wieldables;

import oceanwielder.wieldables.shields.*;
import oceanwielder.wieldables.weapons.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.HashMap;

public class WieldableLibrary {
    public static HashMap<String, AbstractWieldable> wieldables = new HashMap<>();
    public static HashMap<String, AbstractWeapon> weapons = new HashMap<>();
    public static HashMap<String, AbstractShield> shields = new HashMap<>();
    public static AbstractWeapon defaultWeapon;
    public static AbstractShield defaultShield;

    public static void initialize() {
        add(new Cutlass());
        add(new ThrowingStar());
        defaultWeapon = weapons.get(Cutlass.ID);

        add(new Buckler());
        defaultShield = shields.get(Buckler.ID);
    };

    public static AbstractWieldable get(String id) {
        return wieldables.get(id);
    }

    public static AbstractWieldable getRandomWieldable(AbstractWieldable exclude) {
        while (true) {
            AbstractWieldable wieldable = ((AbstractWieldable)wieldables.values().toArray()[AbstractDungeon.cardRandomRng.random(wieldables.size() - 1)]);
            if (exclude == null || wieldable.getClass() != exclude.getClass())
                return wieldable.makeCopy();
        }
    }

    public static AbstractWieldable getRandomWieldable() {
        return getRandomWieldable(null);
    }

    private static void add(AbstractWieldable wieldable) {
        wieldables.put(wieldable.id, wieldable);
        if (wieldable instanceof AbstractWeapon)
            weapons.put(wieldable.id, (AbstractWeapon)wieldable);
        if (wieldable instanceof AbstractShield)
            shields.put(wieldable.id, (AbstractShield)wieldable);
    }
}
