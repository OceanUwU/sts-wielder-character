package oceanwielder.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class EasyModalChoiceAction extends SelectCardsCenteredAction {
    @SuppressWarnings("unchecked")
    public EasyModalChoiceAction(ArrayList<? extends AbstractCard> list, int amount, String textforSelect) {
        super((ArrayList<AbstractCard>)list, amount, textforSelect, (cards) -> {
            for (AbstractCard q : cards)
                q.onChoseThisOption();
        });
    }

    public EasyModalChoiceAction(ArrayList<? extends AbstractCard> list, int amount) {
        this(list, amount, "Choose.");
    }

    public EasyModalChoiceAction(ArrayList<? extends AbstractCard> list) {
        this(list, 1, "Choose.");
    }
}