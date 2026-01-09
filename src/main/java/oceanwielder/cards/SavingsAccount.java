package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;
import oceanwielder.powers.LambdaPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class SavingsAccount extends AbstractWielderCard {
    public final static String ID = makeID(SavingsAccount.class.getSimpleName());

    public SavingsAccount() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
        setSecondMagic(1, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void atStartOfTurnPostDraw() {
                flash();
                getTix(amount);
            }
            
            @Override public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
        if (secondMagic > 0)
            applyToSelf(new DrawPower(p, -secondMagic));
    }
}