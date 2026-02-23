package oceanwielder.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.ArrayList;
import oceanwielder.WielderMod;
import oceanwielder.actions.GuardAction;
import oceanwielder.actions.HitAction;
import oceanwielder.actions.HitAllAction;
import oceanwielder.util.TexLoader;
import oceanwielder.wieldables.AbstractWieldable;

public abstract class AbstractWielderPower extends AbstractPower {
    public int amount2 = -1;
    public boolean isTwoAmount = false;
    public static Color redColor2 = Color.RED.cpy();
    public static Color greenColor2 = Color.GREEN.cpy();
    public boolean canGoNegative2 = false;

    public AbstractWielderPower(String ID, String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        this.ID = ID;
        this.isTurnBased = isTurnBased;

        this.name = NAME;

        this.owner = owner;
        this.amount = amount;
        this.type = powerType;

        Texture normalTexture = TexLoader.getTexture(WielderMod.modID + "Resources/images/powers/" + ID.replaceAll(WielderMod.modID + ":", "") + "32.png");
        Texture hiDefImage = TexLoader.getTexture(WielderMod.modID + "Resources/images/powers/" + ID.replaceAll(WielderMod.modID + ":", "") + "84.png");
        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }

        if (!(this instanceof LambdaPower))
            updateDescription();
    }

    public void onWield(AbstractWieldable wieldable) {}
    public void onSpendTix(ArrayList<AbstractCard> drawn) {}
    public int changeCardsDrawnByTix(int drawn) { return drawn; }
    public void onGuard(AbstractCard c, AbstractMonster m, boolean fromCard) {}
    public void onHit(AbstractCard c, AbstractMonster m, boolean fromCard) {}
    public void onHitAll(AbstractCard c, boolean fromCard) {}
    public void onGuard(AbstractCard c, AbstractMonster m, boolean fromCard, GuardAction action) { onGuard(c, m, fromCard); }
    public void onHit(AbstractCard c, AbstractMonster m, boolean fromCard, HitAction action) { onHit(c, m, fromCard); }
    public void onHitAll(AbstractCard c, boolean fromCard, HitAllAction action) { onHitAll(c,fromCard); }
    public void onLoseHPFromWeight(int weight) {}
    public boolean shouldCancelWeightHPLoss(int weight) { return false; }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (!isTwoAmount)
            return;
        if (amount2 > 0) {
            if (!isTurnBased) {
                greenColor2.a = c.a;
                c = greenColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount2), x, y + 15.0F * Settings.scale, fontScale, c);
        } else if (amount2 < 0 && canGoNegative2) {
            redColor2.a = c.a;
            c = redColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount2), x, y + 15.0F * Settings.scale, fontScale, c);
        }
    }
}