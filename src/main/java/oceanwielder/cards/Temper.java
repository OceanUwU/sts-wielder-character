package oceanwielder.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import oceanwielder.powers.LambdaPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Temper extends AbstractWielderCard {
    public final static String ID = makeID(Temper.class.getSimpleName());

    public Temper() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(3, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF, false, p, magicNumber) {
            @Override
            public void onHit(AbstractCard c, AbstractMonster m, boolean fromCard) {
                onHitAll(c, fromCard);
            }

            @Override
            public void onHitAll(AbstractCard c, boolean fromCard) {
                flash();
                applyToSelf(new VigorPower(p, amount));
            }
            
            @Override public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
    }
}