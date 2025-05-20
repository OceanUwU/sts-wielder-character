package oceanwielder.cards;

import oceanwielder.actions.WieldAction;
import oceanwielder.wieldables.WieldableLibrary;

import static oceanwielder.util.Wiz.*;

public class EasyModalChoiceCards {
    public static EasyModalChoiceCard wieldRandomWeapon = new EasyModalChoiceCard("WieldRandomWeapon", () -> {
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.weapons, true)));
    });

    public static EasyModalChoiceCard wieldRandomShield = new EasyModalChoiceCard("WieldRandomShield", () -> {
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.shields, true)));
    });

    public static EasyModalChoiceCard wieldRandomBoth = new EasyModalChoiceCard("WieldRandomBoth", () -> {
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.weapons, true)));
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.shields, true)));
    });
}