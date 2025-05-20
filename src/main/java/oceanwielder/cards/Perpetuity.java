package oceanwielder.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import oceanwielder.powers.LambdaPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Perpetuity extends AbstractWielderCard {
    public final static String ID = makeID(Perpetuity.class.getSimpleName());

    public Perpetuity() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
        setExhaust(true, false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override public void updateDescription() {
                description = amount == 1 ? strings[1] : (strings[2] + amount + strings[3]);
            }
        });
    }

    @SpirePatch(clz=VigorPower.class, method="onUseCard")
    public static class VigorPatch {
        @SpireInsertPatch(rloc=6)
        public static SpireReturn<Void> Insert(VigorPower __instance) {
            if (__instance.owner.hasPower(ID)) {
                atb(new ReducePowerAction(__instance.owner, __instance.owner, ID, 1));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}