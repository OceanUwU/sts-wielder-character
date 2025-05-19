package oceanwielder.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import oceanwielder.actions.GuardAction;
import oceanwielder.actions.HitAction;
import oceanwielder.actions.HitAllAction;
import oceanwielder.actions.WieldAction;
import oceanwielder.characters.TheWielder;
import oceanwielder.powers.AegisPower;
import oceanwielder.util.Wiz;
import oceanwielder.wieldables.AbstractWieldable;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;
import static oceanwielder.WielderMod.modID;
import static oceanwielder.util.Wiz.*;

public abstract class AbstractWielderCard extends CustomCard {
    protected static String[] sharedStrings = null;
    protected final CardStrings cardStrings;

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int hits;
    public int baseHits;
    public boolean upgradedHits;
    public boolean isHitsModified;

    public int guards;
    public int baseGuards;
    public boolean upgradedGuards;
    public boolean isGuardsModified;

    private int magicUpgrade, secondMagicUpgrade, hitsUpgrade, guardsUpgrade, baseCost, costUpgrade;
    private boolean upgradesExhaust = false;
    private boolean upgradedExhaust;
    private boolean upgradesEthereal = false;
    private boolean upgradedEthereal;
    private boolean upgradesInnate = false;
    private boolean upgradedInnate;
    private boolean upgradesRetain = false;
    private boolean upgradedRetain;

    public boolean usesHits, usesGuards;
    public boolean showWeaponDequipValue, showShieldDequipValue;
    public AbstractWieldable weapon;
    public AbstractWieldable shield;

    private boolean betaTexture;

    @SpireEnum public static CardTags Stab;
    @SpireEnum public static CardTags Jab;

    public AbstractWielderCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }

    public AbstractWielderCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        betaTexture = getCardTextureString(cardID.replace(modID + ":", ""), type).contains("beta/");
        baseCost = cost;
        if (sharedStrings == null)
            sharedStrings = CardCrawlGame.languagePack.getCardStrings(makeID("AbstractWielderCard")).EXTENDED_DESCRIPTION;
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        rawDescription = "";
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void initializeDescription() {
        if (cardStrings != null) {
            rawDescription = ("{@@}" + cardStrings.DESCRIPTION)
                .replace("!HIT!", sharedStrings[1] + sharedStrings[0])
                .replace("!HITALL!", sharedStrings[2] + sharedStrings[0])
                .replace("!HITRANDOM!", sharedStrings[3] + sharedStrings[0])
                .replace("!GUARD!", sharedStrings[5] + sharedStrings[4])
                .replace("!WEIGHT!", sharedStrings[7]);
            if (weapon != null)
                rawDescription = rawDescription.replace("!WW!", sharedStrings[6] + weapon.id);
            if (shield != null)
                rawDescription = rawDescription.replace("!WS!", sharedStrings[6] + shield.id);
            if (selfRetain || isInnate || isEthereal) {
                rawDescription = "NL " + rawDescription;
                if (selfRetain)
                    rawDescription = sharedStrings[9] + " " + rawDescription;
                if (isInnate)
                    rawDescription = sharedStrings[10] + " " + rawDescription;
                if (isEthereal)
                    rawDescription = sharedStrings[11] + " " + rawDescription;
            }
            if (exhaust)
                rawDescription += (cardStrings.DESCRIPTION.length() == 0 ? "" : " NL ") + sharedStrings[8];
        }
        super.initializeDescription();
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString = makeImagePath("cards/" + cardName + ".png");
        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists())
            switch (cardType) {
                case ATTACK:
                    textureString = makeImagePath("cards/beta/attack.png");
                    break;
                case POWER:
                    textureString = makeImagePath("cards/beta/power.png");
                    break;
                default:
                    textureString = makeImagePath("cards/beta/skill.png");
                    break;
            }
        return textureString;
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        hits = baseHits;
        isHitsModified = false;
        guards = baseGuards;
        isGuardsModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedHits) {
            hits = baseHits;
            isHitsModified = true;
        }
        if (upgradedGuards) {
            guards = baseGuards;
            isGuardsModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeHits(int amount) {
        baseHits += amount;
        hits = baseHits;
        upgradedHits = true;
        if (baseHits > 0) {
            usesHits = true;
            baseDamage = 0;
        }
    }

    protected void upgradeGuards(int amount) {
        baseGuards += amount;
        guards = baseGuards;
        upgradedGuards = true;
        if (baseGuards > 0) {
            usesGuards = true;
            baseBlock = 0;
        }
    }

    protected void setMagic(int amount) {
        baseMagicNumber = magicNumber = amount;
    }
    
    protected void setMagic(int amount, int upgrade) {
        setMagic(amount);
        magicUpgrade = upgrade;
    }

    protected void setSecondMagic(int amount) {
        baseSecondMagic = secondMagic = amount;
    }
    
    protected void setSecondMagic(int amount, int upgrade) {
        setSecondMagic(amount);
        secondMagicUpgrade = upgrade;
    }

    protected void setHits(int amount) {
        baseHits = hits = amount;
        if (baseHits > 0) {
            usesHits = true;
            baseDamage = 0;
        }
    }
    
    protected void setHits(int amount, int upgrade) {
        setHits(amount);
        hitsUpgrade = upgrade;
    }

    protected void setGuards(int amount) {
        baseGuards = guards = amount;
        if (baseGuards > 0) {
            usesGuards = true;
            baseBlock = 0;
        }
    }
    
    protected void setGuards(int amount, int upgrade) {
        setGuards(amount);
        guardsUpgrade = upgrade;
    }

    protected void setCostUpgrade(int upgrade) {
        costUpgrade = upgrade;
    }

    protected void setExhaust(boolean exhausts, boolean exhaustsWhenUpgraded) {
        setExhaust(exhausts);
        upgradesExhaust = true;
        upgradedExhaust = exhaustsWhenUpgraded;
        if (upgraded) setExhaust(exhaustsWhenUpgraded);
    }

    protected void setExhaust(boolean exhausts) {
        exhaust = exhausts;
    }

    protected void setEthereal(boolean ethereal, boolean etherealWhenUpgraded) {
        setEthereal(ethereal);
        upgradesEthereal = true;
        upgradedEthereal = etherealWhenUpgraded;
        if (upgraded) setEthereal(etherealWhenUpgraded);
    }

    protected void setEthereal(boolean exhausts) {
        isEthereal = exhausts;
    }

    protected void setInnate(boolean innate, boolean innateWhenUpgraded) {
        setInnate(innate);
        upgradesInnate = true;
        upgradedInnate = innateWhenUpgraded;
        if (upgraded) setInnate(innateWhenUpgraded);
    }

    protected void setInnate(boolean innate) {
        isInnate = innate;
    }

    protected void setRetain(boolean retains, boolean retainsWhenUpgraded) {
        setRetain(retains);
        upgradesRetain = true;
        upgradedRetain = retainsWhenUpgraded;
        if (upgraded) setRetain(retainsWhenUpgraded);
    }

    protected void setRetain(boolean retains) {
        selfRetain = retains;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            if (magicUpgrade != 0)
                upgradeMagicNumber(magicUpgrade);
            if (secondMagicUpgrade != 0)
                upgradeSecondMagic(secondMagicUpgrade);
            if (hitsUpgrade != 0)
                upgradeHits(hitsUpgrade);
            if (guardsUpgrade != 0)
                upgradeGuards(guardsUpgrade);
            if (costUpgrade != 0)
                upgradeBaseCost(baseCost + costUpgrade);
            if (upgradesExhaust)
                exhaust = upgradedExhaust;
            if (upgradesEthereal)
                isEthereal = upgradedEthereal;
            if (upgradesInnate)
                isInnate = upgradedInnate;
            if (upgradesRetain)
                selfRetain = upgradedRetain;
            upp();
            initializeDescription();
        }
    }

    public void upp() {};

    private AbstractGameAction hitAction(AbstractMonster m, int amt) {
        return new HitAction(m, amt);
    }

    protected void hit(AbstractMonster m) {
        atb(hitAction(m, hits));
    }
    
    protected void hitTop(AbstractMonster m) {
        att(hitAction(m, hits));
    }

    protected void hit(AbstractMonster m, int amt) {
        atb(hitAction(m, amt));
    }
    
    protected void hitTop(AbstractMonster m, int amt) {
        att(hitAction(m, amt));
    }

    protected void hitRandom(AbstractMonster m) {
        atb(hitAction(null, hits));
    }

    protected void hitRandomTop(AbstractMonster m) {
        att(hitAction(null, hits));
    }

    protected void hitRandom(AbstractMonster m, int amt) {
        atb(hitAction(null, amt));
    }

    protected void hitRandomTop(AbstractMonster m, int amt) {
        att(hitAction(null, amt));
    }

    private AbstractGameAction hitAllAction(int amt) {
        return new HitAllAction(amt);
    }

    protected void hitAll() {
        atb(hitAllAction(hits));
    }

    protected void hitAllTop() {
        att(hitAllAction(hits));
    }

    protected void hitAll(int amt) {
        atb(hitAllAction(amt));
    }

    protected void hitAllTop(int amt) {
        att(hitAllAction(amt));
    }

    private AbstractGameAction guardAction(int amt) {
        return new GuardAction(amt);
    }

    protected void guard() {
        atb(guardAction(guards));
    }

    protected void guardTop() {
        att(guardAction(guards));
    }

    protected void guard(int amt) {
        atb(guardAction(amt));
    }

    protected void guardTop(int amt) {
        att(guardAction(amt));
    }

    protected void wield(AbstractWieldable wieldable) {
        atb(new WieldAction(wieldable));
    }

    protected void bearWeight() {
        Wiz.bearWeight(secondMagic);
    }

    protected void bearWeightTop() {
        Wiz.bearWeightTop(secondMagic);
    }

    protected void gainVigor(int amount) {
        if (amount > 0)
            applyToSelf(new VigorPower(adp(), amount));
    }

    protected void gainAegis(int amount) {
        if (amount > 0)
            applyToSelf(new AegisPower(adp(), amount));
    }

    @SpireOverride
    protected void renderPortrait(SpriteBatch sb) {
        SpireSuper.call(sb);
        if (betaTexture && portrait != null) {
            int num = cardID.hashCode();
            float r = num % 50;
            float g = Math.round(num / 50) % 50;
            float b = Math.round(num / Math.pow(50, 2)) % 50;
            sb.setColor(new Color(0.5f + r / 100f, 0.5f + g / 100f, 0.5f + b / 100f, transparency));
            sb.draw(portrait.getTexture(), current_x - 125f, current_y - 23f, 125f, 23f, 250f, 190f, drawScale * Settings.scale, drawScale * Settings.scale, angle, 0, 0, 250, 190, false, false);
        }
    }
}