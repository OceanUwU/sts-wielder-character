package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import oceanwielder.powers.LambdaPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Splurge extends AbstractWielderCard {
    public final static String ID = makeID(Splurge.class.getSimpleName());

    public Splurge() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        setMagic(1);
        setSecondMagic(0, +2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public int changeCardsDrawnByTix(int drawn) {
                return drawn + amount;
            }
   
            public void atEndOfRound() {
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            }
            
            @Override public void updateDescription() {
                description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
            }
        });
        if (secondMagic > 0) {
            applyToSelf(new LambdaPower(ID + "Block", exDesc, exDesc[4], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, secondMagic) {
                @Override
                public void onSpendTix(ArrayList<AbstractCard> drawn) {
                    flash();
                    att(new GainBlockAction(p, amount));
                }
   
                public void atEndOfRound() {
                    addToBot(new RemoveSpecificPowerAction(owner, owner, this));
                }
                
                @Override public void updateDescription() {
                    description = strings[5] + amount + strings[6];
                }
            });
        }
    }
}