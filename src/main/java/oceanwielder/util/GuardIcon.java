package oceanwielder.util;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import java.util.ArrayList;
import java.util.List;

public class GuardIcon extends AbstractCustomIcon {
    public final static String ID = makeID("Guard");
    private static GuardIcon singleton;

    public GuardIcon() {
        super(ID, TexLoader.getTexture(makeImagePath("ui/guard.png")));
    }

    public static GuardIcon get() {
        if (singleton == null)
            singleton = new GuardIcon();
        return singleton;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> list = new ArrayList<TooltipInfo>();
        list.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("guard")), BaseMod.getKeywordDescription(makeID("guard"))));
        return list;
    }
}
