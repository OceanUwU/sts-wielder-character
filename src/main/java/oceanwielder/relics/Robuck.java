package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import oceanwielder.actions.GainTixAction;
import oceanwielder.characters.TheWielder;

public class Robuck extends AbstractWielderRelic {
    public static final String ID = makeID("Robuck");
    public static final int POWER = 2;

    public Robuck() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }

    public void atBattleStart() {
        flash();
        att(new GainTixAction(POWER));
        att(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    protected void initializeTips() {
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("tix")), BaseMod.getKeywordDescription(makeID("tix"))));
    }

    @Override
    public String getUpdatedDescription() {
        String str = DESCRIPTIONS[0];
        for (int i = 0; i < POWER; i++)
            str += DESCRIPTIONS[1];
        return str + DESCRIPTIONS[2];
    }
}
