package FrontEnd;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SubItem extends Component {
    private ActionListener actionListener;
    private boolean isSelected = false;
    private JCheckBoxMenuItem item;

    public SubItem(String nome, ActionListener actionListener) {
        super(nome);
        this.actionListener = actionListener;
        this.isSelected = false;
    }
    public void setFalse() {
        item.setSelected(false);
    }
    public boolean info(){
        return item.isSelected();
    }
    public boolean isSelected() {
        return this.isSelected;
    }
    @Override
    public JCheckBoxMenuItem build() {
        item = new JCheckBoxMenuItem(nome);
        item.setSelected(isSelected);
        if (actionListener != null) {
            item.addActionListener(actionListener);
        }
        return item;
    }
}
