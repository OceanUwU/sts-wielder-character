package oceanwielder.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import oceanwielder.powers.AegisPower;
import oceanwielder.powers.LambdaPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class ProfitMargin extends AbstractWielderCard {
    public final static String ID = makeID(ProfitMargin.class.getSimpleName());

    public ProfitMargin() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void onSpendTix(ArrayList<AbstractCard> drawn) {
                flash();
                applyToSelfTop(new AegisPower(p, amount));
            }
        
            @Override public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
    }
}