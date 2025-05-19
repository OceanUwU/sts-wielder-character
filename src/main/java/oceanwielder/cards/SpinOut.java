package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class SpinOut extends AbstractWielderCard {
    public final static String ID = makeID(SpinOut.class.getSimpleName());

    public SpinOut() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setMagic(3, +1);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            hitRandomTop(AbstractDungeon.cardRandomRng.random(magicNumber - secondMagic) + secondMagic);
        });
    }
}