package Strategy;

import BackEnd.Libro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StrategyConcreto {
    //private ArrayList<FiltroStrategy> filtriAttivi = new ArrayList<>();
    private final Map<String, FiltroStrategy> filtriAttivi = new HashMap<>();

    public void aggiungiFiltro(String valore,FiltroStrategy filtro) {
        System.out.println(filtro);
        filtriAttivi.put(valore, filtro);
    }
    public void rimuoviFiltro(String filtro) {
        System.out.println(filtriAttivi);
        filtriAttivi.remove(filtro);
    }
    public void pulisciFiltri() {
        filtriAttivi.clear();
    }


    public boolean filtriAttivi() {
        if( filtriAttivi.isEmpty()) {
            return false;
        }
        return true;
    }
    public ArrayList<Libro> applicaFiltri(ArrayList<Libro> libri) {
        if (filtriAttivi.isEmpty()) {
            return new ArrayList<>(libri);
        }
        ArrayList<Libro> libriFiltrati = new ArrayList<>();
        for (Libro libro : libri) {
            boolean passaTuttiIFiltri = true;
            for (FiltroStrategy filtro : filtriAttivi.values()) {
                if (!filtro.filtra(libro)) {
                    passaTuttiIFiltri = false;
                    break;
                }
            }
            if (passaTuttiIFiltri) {
                libriFiltrati.add(libro);
            }
        }
        return libriFiltrati;
    }
}
