package Strategy;

import Template.Libro;

public class FiltroPerGenere implements FiltroStrategy {
    private final String genere;

    public FiltroPerGenere(String genere) {
        this.genere = genere;
    }
    @Override
    public boolean filtra(Libro libro) {
        return libro.getGenere().equalsIgnoreCase(genere);
    }
}
