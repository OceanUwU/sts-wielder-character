package oceanwielder.wieldables;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import oceanwielder.characters.TheWielder;
import oceanwielder.wieldables.AbstractWieldable;

public class WieldableSlot {
    public boolean shouldRender = false;

    public ArrayList<AbstractWieldable> dequipped = new ArrayList<>();
    public AbstractWieldable wieldable;
    private AbstractWieldable defaultWieldable;

    public WieldableSlot(AbstractWieldable defaultWieldable) {
        this.defaultWieldable = defaultWieldable;
        wieldable = reset();
    }

    public AbstractWieldable reset() {
        shouldRender = AbstractDungeon.player instanceof TheWielder;
        dequipped.clear();
        wieldable = defaultWieldable.makeCopy();
        return wieldable;
    }

    public void update() {
        wieldable.update();
        for (AbstractWieldable w : dequipped)
            if (w != wieldable)
                w.update();
        for (AbstractWieldable w : dequipped)
            if (w.done) {
                dequipped.remove(w);
                break;
            }
    }

    public void updateAnimation() {
        wieldable.updateAnimation();
        for (AbstractWieldable w : dequipped)
            if (w != wieldable)
                w.updateAnimation();
    }

    public void render(SpriteBatch sb) {
        wieldable.render(sb);
        for (AbstractWieldable w : dequipped)
            if (w != wieldable)
                w.render(sb);
        wieldable.renderText(sb);
    }
}
