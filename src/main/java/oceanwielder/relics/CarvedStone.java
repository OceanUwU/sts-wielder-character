package oceanwielder.relics;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import oceanwielder.characters.TheWielder;
import oceanwielder.powers.AegisPower;
import org.apache.commons.codec.binary.StringUtils;

public class CarvedStone extends AbstractWielderRelic {
    public static final String ID = makeID("CarvedStone");
    public static final int POWER = 1;

    public CarvedStone() {
        super(ID, RelicTier.BOSS, LandingSound.SOLID, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }
    
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(RemarkablyThrowableRock.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, RemarkablyThrowableRock.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
        } else
            super.obtain();
    }
  
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(RemarkablyThrowableRock.ID);
    }

   public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        atb(new ApplyPowerAction(adp(), adp(), new VigorPower(adp(), POWER), POWER, true));
        atb(new ApplyPowerAction(adp(), adp(), new AegisPower(adp(), POWER), POWER, true));
   }

   @Override
   public String getUpdatedDescription() {
       return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
   }
}
