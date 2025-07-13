package Observer;

import Template.Libro;

import java.util.ArrayList;

public class LibreriaSubject {
    private final ArrayList<InterfacciaObserver> observers = new ArrayList<>();
    //private ArrayList<Libro> ultimiLibri;
    //private boolean notificato = false;

   //= public ArrayList<Libro> getUltimiLibri() {return ultimiLibri;}

    public void addObserver(InterfacciaObserver observer) {
        observers.add(observer);
    }

    public ArrayList<InterfacciaObserver> getObservers() {
        return observers;
    }
    public void removeObserver(InterfacciaObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(ArrayList<Libro> libri) {
        for (InterfacciaObserver observer : observers) {
            observer.update(libri);
        }
    }
}
