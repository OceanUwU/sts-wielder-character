package oceanwielder.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.Arrays;
import java.util.List;
import oceanwielder.wieldables.WieldableLibrary;

import static oceanwielder.WielderMod.makeID;

public class Defend extends AbstractWielderCard {
    public final static String ID = makeID("Defend");

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        setGuards(1);
        setMagic(0, +2);
        tags.add(CardTags.STARTER_DEFEND);
    }

    public List<TooltipInfo> getCustomTooltips() {
        return Arrays.asList(new TooltipInfo(WieldableLibrary.defaultShield.name, GameDictionary.keywords.get(WieldableLibrary.defaultShield.id.toLowerCase())));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        gainAegis(magicNumber);
    }
}