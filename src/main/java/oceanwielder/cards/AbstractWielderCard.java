package oceanwielder.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.GuardAction;
import oceanwielder.actions.HitAction;
import oceanwielder.actions.HitAllAction;
import oceanwielder.actions.WieldAction;
import oceanwielder.characters.TheWielder;
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
    public boolean usesHits, usesGuards;
    public boolean showWeaponDequipValue, showShieldDequipValue;
    public AbstractWieldable weapon;
    public AbstractWieldable shield;

    public AbstractWielderCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, TheWielder.Enums.OCEAN_WIELDER_COLOUR);
    }

    public AbstractWielderCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
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
                .replace("!GUARD!", sharedStrings[5] + sharedStrings[4]);
        }
        super.initializeDescription();
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString = makeImagePath("cards/" + cardName + ".png");
        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists())
            switch (cardType) {
                case ATTACK:
                    textureString = makeImagePath("cards/betaattack.png");
                    break;
                case POWER:
                    textureString = makeImagePath("cards/betapower.png");
                    break;
                default:
                    textureString = makeImagePath("cards/betaskill.png");
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
            upp();
        }
    }

    public void upp() {};

    private AbstractGameAction hitAction(AbstractMonster m) {
        return new HitAction(m, hits);
    }

    protected void hit(AbstractMonster m) {
        atb(hitAction(m));
    }
    
    protected void hitTop(AbstractMonster m) {
        att(hitAction(m));
    }

    private AbstractGameAction hitAllAction() {
        return new HitAllAction(hits);
    }

    protected void hitAll() {
        atb(hitAllAction());
    }

    protected void hitAllTop() {
        att(hitAllAction());
    }

    private AbstractGameAction guardAction() {
        return new GuardAction(guards);
    }

    protected void guard() {
        atb(guardAction());
    }

    protected void guardTop() {
        att(guardAction());
    }

    protected void wield(AbstractWieldable wieldable) {
        att(new WieldAction(wieldable));
    }

    public String cardArtCopy() {
        return null;
    }
}