package FrontEnd;

import javax.swing.*;

public abstract class Component extends JComponent {
    protected String nome;

    public Component(String nome) {
        this.nome = nome;
    }
    public void aggiungi(Component componente) {
        throw new UnsupportedOperationException("Operazione non supportata su questo componente.");
    }

    public void rimuovi(Component componente) {
        throw new UnsupportedOperationException("Operazione non supportata su questo componente.");
    }

    public Component getChild(int index) {
        throw new UnsupportedOperationException("Operazione non supportata su questo componente.");
    }

    public abstract JComponent build();
}
