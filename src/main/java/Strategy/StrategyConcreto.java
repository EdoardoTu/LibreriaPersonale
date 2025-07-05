package Strategy;

import Template.Libro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StrategyConcreto {
    private FiltroStrategy filtroStrategy;
    //private ArrayList<FiltroStrategy> filtriAttivi = new ArrayList<>();
    private final Map<String, FiltroStrategy> filtriAttivi = new HashMap<>();
    public void setStrategy(FiltroStrategy filtroStrategy) {
        this.filtroStrategy = filtroStrategy;
    }

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
        return libri.stream()
                .filter(libro -> filtriAttivi.values().stream()
                        .allMatch(f -> f.filtra(libro)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
