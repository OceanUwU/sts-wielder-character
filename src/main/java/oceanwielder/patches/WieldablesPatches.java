package oceanwielder.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import oceanwielder.WielderMod;
import oceanwielder.cards.AbstractWielderCard;
import oceanwielder.wieldables.weapons.AbstractWeapon;

public class WieldablesPatches {
    @SpirePatch(clz=AbstractPlayer.class, method="renderPlayerBattleUi")
    public static class RenderPatch {
        public static void Prefix(AbstractPlayer __instance, SpriteBatch sb) {
            if (WielderMod.weaponSlot.shouldRender)
                WielderMod.weaponSlot.render(sb);
            if (WielderMod.shieldSlot.shouldRender)
                WielderMod.shieldSlot.render(sb);
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="combatUpdate")
    public static class UpdatePatch {
        public static void Postfix() {
            if (WielderMod.weaponSlot.shouldRender)
                WielderMod.weaponSlot.update();
            if (WielderMod.shieldSlot.shouldRender)
                WielderMod.shieldSlot.update();
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="update")
    public static class UpdateAnimationPatch {
        public static void Postfix() {
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.EVENT && WielderMod.weaponSlot.shouldRender) {
                WielderMod.weaponSlot.updateAnimation();
                WielderMod.shieldSlot.updateAnimation();
            }
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="preBattlePrep")
    public static class ResetPatch {
        public static void Prefix() {
            WielderMod.weaponSlot.reset();
            WielderMod.shieldSlot.reset();
        }
    }

    private static void onSelectCard(AbstractPlayer p) {
        if (p.hoveredCard instanceof AbstractWielderCard) {
            AbstractWielderCard c = (AbstractWielderCard)p.hoveredCard;
            if (c.usesGuards || c.usesHits) {
                if (c.usesGuards)
                    WielderMod.shieldSlot.wieldable.fontScale *= 1.5f;
                if (c.usesHits)
                    WielderMod.weaponSlot.wieldable.fontScale *= 1.5f;
            }
            if (c.showWeaponDequipValue)
                WielderMod.weaponSlot.wieldable.showDequipValue();
            if (c.showShieldDequipValue)
                WielderMod.shieldSlot.wieldable.showDequipValue();
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="updateInput")
    public static class HoverPatch {
        @SpireInsertPatch(loc=924)
        public static void Insert(AbstractPlayer p) {
            onSelectCard(p);
        }

        public static void Prefix(AbstractPlayer p) {
            WielderMod.weaponSlot.wieldable.applyPowers();
            WielderMod.shieldSlot.wieldable.applyPowers();
            if ((p.isDraggingCard || p.inSingleTargetMode) && p.isHoveringDropZone && p.hoveredCard instanceof AbstractWielderCard && ((AbstractWielderCard)p.hoveredCard).usesHits) {
                AbstractMonster hoveredMonster = ReflectionHacks.getPrivate(p, AbstractPlayer.class, "hoveredMonster");
                if (p.hoveredCard.target == AbstractCard.CardTarget.ENEMY && p.inSingleTargetMode && hoveredMonster != null)
                    ((AbstractWeapon)WielderMod.weaponSlot.wieldable).calculateDamage(hoveredMonster);
            }
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="manuallySelectCard")
    public static class SelectPatch {
        @SpireInsertPatch(loc=1570)
        public static void Insert(AbstractPlayer p) {
            onSelectCard(p);
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="releaseCard")
    public static class ReleasePatch {
        public static void Prefix(AbstractPlayer p) {
            WielderMod.weaponSlot.wieldable.applyPowers();
            WielderMod.shieldSlot.wieldable.applyPowers();
            WielderMod.weaponSlot.wieldable.hideDequipValue();
            WielderMod.shieldSlot.wieldable.hideDequipValue();
        }
    }

    @SpirePatch(clz=AbstractDungeon.class, method="onModifyPower")
    public static class ModifyPowerPatch {
        public static void Prefix() {
            if (AbstractDungeon.player != null) {
                WielderMod.weaponSlot.wieldable.applyPowers();
                WielderMod.shieldSlot.wieldable.applyPowers();
            }
        }
    }
}