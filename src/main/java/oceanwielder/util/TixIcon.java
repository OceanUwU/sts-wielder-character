package oceanwielder.util;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import java.util.ArrayList;
import java.util.List;

public class TixIcon extends AbstractCustomIcon {
    public final static String ID = makeID("Tix");
    private static TixIcon singleton;

    public TixIcon() {
        super(ID, TexLoader.getTexture(makeImagePath("ui/tixicon.png")));
    }

    public static TixIcon get() {
        if (singleton == null)
            singleton = new TixIcon();
        return singleton;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> list = new ArrayList<TooltipInfo>();
        list.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("tix")), BaseMod.getKeywordDescription(makeID("tix"))));
        return list;
    }
}
