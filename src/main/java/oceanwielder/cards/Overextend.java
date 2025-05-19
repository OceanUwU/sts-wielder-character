package oceanwielder.cards;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.powers.LambdaPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Overextend extends AbstractWielderCard {
    public final static String ID = makeID(Overextend.class.getSimpleName());

    public Overextend() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setHits(3);
        setMagic(4, -2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF, false, p, magicNumber) {
            public void onUseCard(AbstractCard card, UseCardAction action) {
                flash();
                atb(new ReducePowerAction(owner, owner, this, 1));
            }
            
            @Override public void updateDescription() {
                description = amount == 1 ? strings[1] : (strings[2] + amount + strings[3]);
            }
        });
    }
}