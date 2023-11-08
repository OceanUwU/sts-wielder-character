package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.EasyModalChoiceAction;
import oceanwielder.wieldables.UnknownWieldable;
import java.util.ArrayList;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class WhateverllDo extends AbstractWielderCard {
    public final static String ID = makeID("WhateverllDo");

    public WhateverllDo() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        weapon = new UnknownWieldable(true);
        shield = new UnknownWieldable(false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<EasyModalChoiceCard> choices = new ArrayList<>();
        choices.add(EasyModalChoiceCards.wieldRandomWeapon);
        choices.add(EasyModalChoiceCards.wieldRandomShield);
        if (upgraded)
            choices.add(EasyModalChoiceCards.wieldRandomBoth);
        atb(new EasyModalChoiceAction(choices));
    }
}