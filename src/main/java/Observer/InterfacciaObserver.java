package Observer;

import BackEnd.Libro;

import java.util.ArrayList;

public interface InterfacciaObserver {
    void update(ArrayList<Libro> listaLibri);
}
