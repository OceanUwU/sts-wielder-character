package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import oceanwielder.characters.TheWielder;

public class SharpRuby extends AbstractWielderRelic {
    public static final String ID = makeID("SharpRuby");
    public static final int POWER = 2;

    public SharpRuby() {
        super(ID, RelicTier.RARE, LandingSound.CLINK, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
        counter = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.hasTag(AbstractCard.CardTags.STARTER_STRIKE) && !card.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) return;
        if (++counter < POWER) return;
        counter = 0;
        flash();
        action.exhaustCard = true;
        atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }
}
