package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.powers.AegisPower;
import oceanwielder.powers.Weight;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Diet extends AbstractWielderCard {
    public final static String ID = makeID(Diet.class.getSimpleName());

    public Diet() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        final int mgc = magicNumber;
        actB(() -> {
            int aegis = pwrAmt(p, Weight.POWER_ID) * mgc;
            if (aegis > 0)
                applyToSelfTop(new AegisPower(p, aegis));
            att(new RemoveSpecificPowerAction(p, p, Weight.POWER_ID));
        });
    }
}