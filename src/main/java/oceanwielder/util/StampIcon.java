package oceanwielder.util;

import static oceanwielder.WielderMod.makeID;
import static oceanwielder.WielderMod.makeImagePath;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import java.util.Arrays;
import java.util.List;

public class StampIcon extends AbstractCustomIcon {
    public final static String ID = makeID("Stamp");
    private static StampIcon singleton;

    public StampIcon() {
        super(ID, TexLoader.getTexture(makeImagePath("ui/stamp.png")));
    }

    public static StampIcon get() {
        if (singleton == null)
            singleton = new StampIcon();
        return singleton;
    }

    @Override
    public List<String> keywordLinks() {
        return Arrays.asList(makeID("stamp"));
    }
}
