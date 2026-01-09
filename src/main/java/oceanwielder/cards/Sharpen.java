package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import oceanwielder.powers.LambdaPower;
import oceanwielder.wieldables.AbstractWieldable;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Sharpen extends AbstractWielderCard {
    public final static String ID = makeID(Sharpen.class.getSimpleName());

    public Sharpen() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(1);
        setCostUpgrade(-1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void onWield(AbstractWieldable wieldable) {
                flash();
                applyToSelf(new StrengthPower(p, amount));
            }
            
            @Override public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
    }
}