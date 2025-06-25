package Observer;

import Template.Libro;

import java.util.ArrayList;

public interface InterfacciaObserver {
    void update(ArrayList<Libro> listaLibri);
}
