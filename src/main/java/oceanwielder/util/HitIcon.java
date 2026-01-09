package oceanwielder.util;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import java.util.ArrayList;
import java.util.List;

public class HitIcon extends AbstractCustomIcon {
    public final static String ID = makeID("Hit");
    private static HitIcon singleton;

    public HitIcon() {
        super(ID, TexLoader.getTexture(makeImagePath("ui/hit.png")));
    }

    public static HitIcon get() {
        if (singleton == null)
            singleton = new HitIcon();
        return singleton;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> list = new ArrayList<TooltipInfo>();
        list.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("hit")), BaseMod.getKeywordDescription(makeID("hit"))));
        return list;
    }
}
