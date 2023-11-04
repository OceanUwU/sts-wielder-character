package oceanwielder.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import java.util.Arrays;
import java.util.List;
import oceanwielder.wieldables.shields.Hologram;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.util.Wiz.*;

public class Diverge extends AbstractWielderCard {
    public final static String ID = makeID("Diverge");

    public Diverge() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setGuards(1);
        setMagic(0, +4);
        shield = new Hologram();
    }

    public List<TooltipInfo> getCustomTooltips() {
        return Arrays.asList(new TooltipInfo(BaseMod.getKeywordTitle(makeID("tix")), BaseMod.getKeywordDescription(makeID("tix"))));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        wield(shield);
        if (magicNumber > 0)
            applyToSelf(new NextTurnBlockPower(p, magicNumber));
    }
}