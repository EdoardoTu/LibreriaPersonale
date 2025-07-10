package FrontEnd;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuComposite extends Component {
    private List<Component> items = new ArrayList<Component>();

    public MenuComposite(String nome) {
        super(nome);
    }
    public void aggiungi(Component componente) {
        items.add(componente);
    }
    public void rimuovi(Component componente) {
        items.remove(componente);
    }
    public Component getChild(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException("Indice fuori dai limiti.");
        }
        return items.get(index);
    }
    @Override
    public JMenu build() {
        JMenu menu = new JMenu(nome);
        for (Component component : items) {
            menu.add(component.build());
        }
        return menu;
    }
    public int getItemCount() {
        return items.size();
    }
}