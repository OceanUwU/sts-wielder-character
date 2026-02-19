package oceanwielder.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import oceanwielder.powers.AegisPower;

import static oceanwielder.WielderMod.makeID;

public class Unleash extends AbstractWielderCard {
    public final static String ID = makeID(Unleash.class.getSimpleName());

    public Unleash() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);
        setGuards(1);
        setHits(1);
        setMagic(2, +1);
    }

    public void applyPowersToBlock() {
        AbstractPower aegis = AbstractDungeon.player.getPower(AegisPower.POWER_ID);
        if (aegis != null) aegis.amount *= magicNumber;
        super.applyPowersToBlock();
        if (aegis != null) aegis.amount /= magicNumber;
    }

    public void applyPowers() {
        AbstractPower vigour = AbstractDungeon.player.getPower(VigorPower.POWER_ID);
        if (vigour != null) vigour.amount *= magicNumber;
        super.applyPowers();
        if (vigour != null) vigour.amount /= magicNumber;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower vigour = AbstractDungeon.player.getPower(VigorPower.POWER_ID);
        if (vigour != null) vigour.amount *= magicNumber;
        super.calculateCardDamage(mo);
        if (vigour != null) vigour.amount /= magicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        hit(m);
    }
}