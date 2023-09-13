package oceanwielder.wieldables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.HashMap;
import oceanwielder.WielderMod;
import oceanwielder.util.TexLoader;

import static oceanwielder.WielderMod.makeImagePath;
import static oceanwielder.util.Wiz.*;

public abstract class AbstractWieldable {
    private static HashMap<String, TextureRegion> TEXTURES = new HashMap<>();
    private static final float SIZE = 192.0f;
    private static final float CENTRE = SIZE / 2.0f;
    private static final float NUM_X_OFFSET = 50.0F * Settings.scale;
    private static final float NUM_Y_OFFSET = 50.0F * Settings.scale;
    private static final Color UNMODIFIED_COLOR = Settings.CREAM_COLOR.cpy();
    private static final Color POSITIVE_COLOR = Color.CHARTREUSE.cpy();
    private static final Color NEGATIVE_COLOR = Color.SALMON.cpy();
    private static final float yAcceleration = -500f;
    public static final int DEQUIP_USE_TIMES = 2;
    private TextureRegion texture;
    public String id;
    public String name;
    public String description;
    public float cX = 0.0f;
    public float cY = 0.0f;
    public float fontScale = 0.01F;
    public Hitbox hb;
    public float angle;
    public float vfxTimer = 0.0F;
    public Color color = Color.WHITE.cpy();
    public boolean dequipping = false;
    public boolean dequipped = false;
    public boolean done = false;
    private float xVel;
    private float yVel;
    private float dequipSpinSpeed;

    public int basePrimary, baseSecondary, baseDequipPower, primary, secondary, dequipPower;
    protected AbstractMonster target;
    protected OrbStrings strings;

    public AbstractWieldable(String id, int basePrimary, int baseSecondary, int baseDequipPower, float xOffset, float yOffset) {
        this.id = id;
        strings = CardCrawlGame.languagePack.getOrbString(id);
        this.name = strings.NAME;
        this.basePrimary = basePrimary;
        this.baseSecondary = baseSecondary;
        this.baseDequipPower = baseDequipPower;
        primary = basePrimary;
        secondary = baseSecondary;
        dequipPower = baseDequipPower;
        updateDescription();
        color.a = 0.0f;
        String texPath = makeImagePath("wieldables/"+id.replace(WielderMod.modID + ":", "")+".png");
        if (!TEXTURES.containsKey(texPath))
            TEXTURES.put(texPath, new TextureRegion(TexLoader.getTexture(texPath)));
        texture = TEXTURES.get(texPath);
        if (CardCrawlGame.isInARun()) {
            cX = xOffset * Settings.scale + AbstractDungeon.player.drawX;
            cY = yOffset * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
            hb = new Hitbox(cX - SIZE * Settings.scale / 2.0F, cY - SIZE * Settings.scale / 2.0F, SIZE * Settings.scale, SIZE * Settings.scale);
        }
    }

    public AbstractMonster getRandomTarget() {
        return AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
    }

    public abstract void use(AbstractMonster mo);
    public void useOnAll() {
        forAllMonstersLivingBackwards(mo -> use(mo));
    }

    public abstract void dequipEffect();

    public void dequip() {
        applyPowers();
        dequipEffect();
        dequipped = true;
        xVel = MathUtils.random(-100f, 100f);
        yVel = MathUtils.random(-25f, 300f);
        dequipSpinSpeed = MathUtils.random(50f, 130f) * (MathUtils.random(0, 1) * 2 - 1);
    };

    public void showDequipValue() {
        dequipping = true;
        fontScale = 1.5f;
    }

    public void hideDequipValue() {
        dequipping = false;
    }

    public void applyPowers() {
        primary = basePrimary;
        secondary = baseSecondary;
        dequipPower = baseDequipPower;
        /*if (CardCrawlGame.isInARun() && AbstractDungeon.player.hasPower(ProficiencyPower.POWER_ID)) {
            int proficiency = AbstractDungeon.player.getPower(ProficiencyPower.POWER_ID).amount;
            if (secondary > -1)
                secondary += proficiency;
            if (dequipPower > -1)
                dequipPower += proficiency;
        }*/
        primary = Math.max(0, primary);
        dequipPower = Math.max(0, dequipPower);
        if (baseSecondary > -1)
            secondary = Math.max(0, secondary);
        updateDescription();
    };

    public void updateDescription() {
        description = strings.DESCRIPTION[0] + primary + strings.DESCRIPTION[1] + dequipPower + strings.DESCRIPTION[2];
    };

    public void update() {
        hb.update();
        if (hb.hovered)
            TipHelper.renderGenericTip(hb.x + 196f * Settings.scale, hb.y + 124f * Settings.scale, name, description);
        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7f);
    };

    public void updateAnimation() {
        vfxTimer += Gdx.graphics.getDeltaTime();
        color.a = MathHelper.fadeLerpSnap(color.a, 1.0f);
        if (dequipped) {
            cX += xVel * Gdx.graphics.getDeltaTime() * Settings.scale;
            cY += yVel * Gdx.graphics.getDeltaTime() * Settings.scale;
            if (cY < -500f)
                done = true;
            angle += dequipSpinSpeed * Gdx.graphics.getDeltaTime();
            yVel += yAcceleration * Gdx.graphics.getDeltaTime();
        }
    };

    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(texture, cX - CENTRE, cY - CENTRE, CENTRE, CENTRE, SIZE, SIZE, Settings.scale, Settings.scale, angle);
    };

    private Color getDynvarColor(int base, int amount) {
        if (amount > base) return POSITIVE_COLOR;
        else if (amount < base) return NEGATIVE_COLOR;
        return UNMODIFIED_COLOR;
    }

    public void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(primary), hb.cX + NUM_X_OFFSET, hb.cY - NUM_Y_OFFSET, getDynvarColor(basePrimary, primary), fontScale);
        if (secondary > -1)
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(secondary), hb.cX - NUM_X_OFFSET, hb.cY - NUM_Y_OFFSET, getDynvarColor(baseSecondary, secondary), fontScale);
        if (dequipping)
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(dequipPower), hb.cX, hb.cY + NUM_Y_OFFSET, getDynvarColor(baseDequipPower, dequipPower), fontScale);
    }

	public AbstractWieldable makeCopy() {
		try {
			return this.getClass().newInstance();
		} catch(InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Failed to auto-generate makeCopy for wieldable: " + id);
		}
	}
}