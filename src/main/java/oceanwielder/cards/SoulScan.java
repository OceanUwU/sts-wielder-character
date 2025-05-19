package oceanwielder.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanwielder.wieldables.weapons.Scythe;

import static oceanwielder.WielderMod.makeID;

public class SoulScan extends AbstractWielderCard {
    public final static String ID = makeID("SoulScan");

    public SoulScan() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setGuards(1);
        weapon = new Scythe();
        initializeDescription();
        setHits(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        guard();
        wield(weapon);
        if (type == CardType.ATTACK)
            hit(m);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        type = CardType.ATTACK;
        if (textureImg != null)
            loadCardImage(textureImg);
    }

    @Override
    public void loadCardImage(String img) {
        if (type == CardType.SKILL)
            img = img.replace(".png", "Skill.png");
        super.loadCardImage(img);
    }

    @Override
    protected Texture getPortraitImage() {
        textureImg = textureImg.replace("Skill", "");
        if (type == CardType.SKILL)
            textureImg = textureImg.replace(".png", "Skill.png");
        return super.getPortraitImage();
    }
}