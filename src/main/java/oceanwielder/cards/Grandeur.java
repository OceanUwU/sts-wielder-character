package oceanwielder.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import oceanwielder.powers.LambdaPower;
import oceanwielder.util.Stamps;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Grandeur extends AbstractWielderCard {
    public final static String ID = makeID(Grandeur.class.getSimpleName());

    public Grandeur() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(1);
        setSecondMagic(0, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void onSpendTix(ArrayList<AbstractCard> drawn) {
                for (AbstractCard c : drawn) {
                    att(new Stamps.Action(c, amount));
                }
            }
            
            @Override public void updateDescription() {
                description = amount == 1 ? strings[1] : (strings[2] + amount + strings[3]);
            }
        });
        getTix(secondMagic);
    }
}