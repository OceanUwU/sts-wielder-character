package oceanwielder.cards;

import oceanwielder.actions.WieldAction;
import oceanwielder.wieldables.WieldableLibrary;

import static oceanwielder.util.Wiz.*;

public class EasyModalChoiceCards {
    public static EasyModalChoiceCard wieldRandomWeapon = new EasyModalChoiceCard("WieldRandomWeapon", () -> {
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.weapons)));
    });

    public static EasyModalChoiceCard wieldRandomShield = new EasyModalChoiceCard("WieldRandomShield", () -> {
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.shields)));
    });

    public static EasyModalChoiceCard wieldRandomBoth = new EasyModalChoiceCard("WieldRandomBoth", () -> {
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.weapons)));
        atb(new WieldAction(WieldableLibrary.getRandomWieldable(WieldableLibrary.shields)));
    });
}