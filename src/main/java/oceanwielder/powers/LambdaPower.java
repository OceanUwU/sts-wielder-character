package oceanwielder.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class LambdaPower extends AbstractWielderPower {
    protected String[] strings;
    
    public LambdaPower(String ID, String[] strings, String name, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        super(ID, name, powerType, isTurnBased, owner, amount);
        this.strings = strings;
        updateDescription();
    }

    public abstract void updateDescription();
}
