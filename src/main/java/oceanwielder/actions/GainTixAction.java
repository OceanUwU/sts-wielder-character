package oceanwielder.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import oceanwielder.util.Tix;

public class GainTixAction extends AbstractGameAction {
    public GainTixAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        isDone = true;
        Tix.gain(amount);
    }
}