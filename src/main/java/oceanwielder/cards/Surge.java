package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Surge extends AbstractWielderCard {
    public final static String ID = makeID(Surge.class.getSimpleName());

    public Surge() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
        setExhaust(true, false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            int amt = magicNumber * pwrAmt(p, VigorPower.POWER_ID);
            if (amt > 0)
                applyToSelf(new VigorPower(p, amt));
        });
    }
}