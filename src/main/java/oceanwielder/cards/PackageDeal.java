package oceanwielder.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.actions.HitAction;
import oceanwielder.util.Stamps;
import oceanwielder.util.Wiz;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class PackageDeal extends AbstractWielderCard {
    public final static String ID = makeID(PackageDeal.class.getSimpleName());

    public PackageDeal() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setHits(0);
        setCostUpgrade(-1);
        setMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(1, exDesc[0], false, false, c -> Stamps.canStamp(c), cards -> {
            cards.forEach(c -> att(new Stamps.Action(c, magicNumber), Wiz.actionify(() -> att(new HitAction(m, Stamps.getStamps(c), this, true)))));
        }));
    }
}