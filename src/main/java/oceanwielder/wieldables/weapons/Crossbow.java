package oceanwielder.wieldables.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import oceanwielder.util.Stamps;
import oceanwielder.util.TexLoader;
import oceanwielder.util.WielderAudio;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;
import static oceanwielder.util.Wiz.*;

public class Crossbow extends AbstractWeapon {
    public static String ID = makeID("Crossbow");

    public Crossbow() {
        super(ID, 9, 0, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        vfxCanAffectAll = true;
    }

    @Override
    public void applyPowers(AbstractCard c) {
        super.applyPowers(c);
        baseDequipPower = basePrimary;
        dequipPower = primary;
        dequipTimes = primaryTimes;
        updateDescription();
    }

    public void useVfx(AbstractMonster m) {
        vfxTop(new ArrowEffect(cX + 25f * Settings.scale, cY, m.hb.cX, m.hb.cY));
    }

    public void dequipEffect() {
        att(new GainBlockAction(adp(), dequipPower));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!Stamps.isStamped(card)) return;
        actB(() -> {
            basePrimary += Stamps.getStamps(card);
            applyPowers();
            AbstractDungeon.effectList.add(new WieldablePulseEffect(this));
            fontScale *= 2f;
        });
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if (dequipTimes == 1)
            description += strings.DESCRIPTION[0] + dequipPower + strings.DESCRIPTION[1] + strings.DESCRIPTION[4];
        else
            description += strings.DESCRIPTION[0] + dequipPower + strings.DESCRIPTION[2] + dequipTimes + strings.DESCRIPTION[3] + strings.DESCRIPTION[4];
    }

    private static class ArrowEffect extends AbstractGameEffect {
        private static TextureAtlas.AtlasRegion IMG = new TextureAtlas.AtlasRegion(TexLoader.getTexture(makeImagePath("vfx/arrow.png")), 0, 0, 128, 128);
        private static final float DURATION = 0.2f;

        private float x, y;
        private float startX, startY, endX, endY;

        public ArrowEffect(float startX, float startY, float endX, float endY) {
            duration = DURATION;
            color = Color.WHITE.cpy();
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            rotation = -(float)Math.toDegrees(Math.atan2(endX - startX, endY - startY)) + 90f;
        }

        public void update() {
            if (duration == DURATION)
                playAudio(WielderAudio.CROSSBOW);
            float progress = 1f - (duration / DURATION);
            x = startX + (endX - startX) * progress;
            y = startY + (endY - startY) * progress;
            if (progress < 0.1f)
                color.a = progress / 0.1f;
            else if (progress > 0.9f)
                color.a = 1 - (progress - 0.9f) / 0.1f;
            else
                color.a = 1f;
            duration -= Gdx.graphics.getDeltaTime();
            if (duration <= 0f)
                isDone = true;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(color);
            sb.draw(IMG, x - 64f, y - 64f, 64, 64, 128, 128, scale, scale, rotation);
        }

        public void dispose() {}
    }
}
