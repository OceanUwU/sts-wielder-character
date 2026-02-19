package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import oceanwielder.characters.TheWielder;
import oceanwielder.util.Stamps;

public class Stamp extends AbstractWielderRelic {
    public static final String ID = makeID("Stamp");
    public static final int POWER = 4;

    public Stamp() {
        super(ID, RelicTier.UNCOMMON, LandingSound.HEAVY, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
  
    public void onEquip() {
        ArrayList<AbstractCard> stampable = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (Stamps.canStamp(c))
                stampable.add(c);
        Collections.shuffle(stampable, new Random(AbstractDungeon.miscRng.randomLong()));
        for (int i = 0; i < POWER; i++) {
            if (stampable.size() - 1 < i) break;
            AbstractCard c = stampable.get(i);
            Stamps.stamp(c);
            float x = MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH;
            float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
        }
    }

    @Override
    protected void initializeTips() {
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("stamp")), BaseMod.getKeywordDescription(makeID("stamp"))));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }
}
