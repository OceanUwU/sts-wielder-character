package oceanwielder.relics;

import static oceanwielder.WielderMod.makeRelicPath;
import static oceanwielder.WielderMod.modID;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import oceanwielder.actions.GuardAction;
import oceanwielder.actions.HitAction;
import oceanwielder.actions.HitAllAction;
import oceanwielder.util.TexLoader;

public abstract class AbstractWielderRelic extends CustomRelic {
    public AbstractCard.CardColor color;

    public AbstractWielderRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(setId, tier, sfx, null);
    }

    public AbstractWielderRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(setId, TexLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + ".png")), tier, sfx);
        outlineImg = TexLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + "Outline.png"));
        this.color = color;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onGuard(AbstractCard c, AbstractMonster m, boolean fromCard, GuardAction action) {}
    public void onHit(AbstractCard c, AbstractMonster m, boolean fromCard, HitAction action) {}
    public void onHitAll(AbstractCard c, boolean fromCard, HitAllAction action) {}
}