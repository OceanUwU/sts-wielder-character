package oceanwielder.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.Arrays;
import java.util.List;
import oceanwielder.wieldables.WieldableLibrary;

import static oceanwielder.WielderMod.makeID;

public class Strike extends AbstractWielderCard {
    public final static String ID = makeID("Strike");

    public Strike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        setHits(1);
        setMagic(0, +2);
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    public List<TooltipInfo> getCustomTooltips() {
        return Arrays.asList(new TooltipInfo(WieldableLibrary.defaultWeapon.name, GameDictionary.keywords.get(WieldableLibrary.defaultWeapon.id.toLowerCase())));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        hit(m);
        gainVigor(magicNumber);
    }
}