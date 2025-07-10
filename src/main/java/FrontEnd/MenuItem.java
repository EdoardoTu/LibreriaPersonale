package FrontEnd;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuItem extends Component {

    private ActionListener actionListener;

    public MenuItem(String nome, ActionListener actionListener) {
        super(nome);
        this.actionListener = actionListener;
    }

    @Override
    public JComponent build() {
        JMenuItem item = new JMenuItem(nome);
        if (actionListener != null) {
            item.addActionListener(actionListener);
        }
        return item;
    }
}

