package Unit;

import Strategy.*;
import Template.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

@DisplayName("Test delle Strategie di Filtro")
public class filtri {


    private ArrayList<Libro> libri;
    private StrategyConcreto filtroManager;

    @BeforeEach
    void setUp() {
        // Preparazione dati di test
        libri = new ArrayList<>();
        libri.add(new Libro("Harry Potter", "J.K. Rowling", 123,
                Libro.Genere.fantasy, 5, Libro.Stato.letto));
        libri.add(new Libro("Dracula", "Bram Stoker", 456,
                Libro.Genere.horror, 4, Libro.Stato.da_leggere));
        libri.add(new Libro("1984", "George Orwell", 789,
                Libro.Genere.fantascienza, 5, Libro.Stato.in_lettura));
        libri.add(new Libro("Il Nome della Rosa", "Umberto Eco", 101,
                Libro.Genere.storico, 4, Libro.Stato.letto));

        filtroManager = new StrategyConcreto();
    }

    @Test
    @DisplayName("Filtro per genere - Fantasy")
    void testFiltroPerGenereFantasy() {
        FiltroPerGenere filtro = new FiltroPerGenere("fantasy");

        // Test con libro fantasy
        assertTrue(filtro.filtra(libri.get(0))); // Harry Potter
        // Test con libro non fantasy
        assertFalse(filtro.filtra(libri.get(1))); // Dracula
    }

    @Test
    @DisplayName("Filtro per stato - Letto")
    void testFiltroPerStatoLetto() {
        FiltroPerStato filtro = new FiltroPerStato("letto");

        assertTrue(filtro.filtra(libri.get(0))); // Harry Potter (letto)
        assertFalse(filtro.filtra(libri.get(1))); // Dracula (da_leggere)
        assertTrue(filtro.filtra(libri.get(3))); // Il Nome della Rosa (letto)
    }

    @Test
    @DisplayName("Applicazione filtro singolo - Solo Fantasy")
    void testApplicazioneFiltroSingoloFantasy() {
        filtroManager.aggiungiFiltro("fantasy", new FiltroPerGenere("fantasy"));

        ArrayList<Libro> risultato = filtroManager.applicaFiltri(libri);

        assertEquals(1, risultato.size());
        assertEquals("Harry Potter", risultato.get(0).getTitolo());
    }

    @Test
    @DisplayName("Applicazione filtri multipli - Fantasy AND Letto")
    void testApplicazioneFiltriMultipli() {
        filtroManager.aggiungiFiltro("fantasy", new FiltroPerGenere("fantasy"));
        filtroManager.aggiungiFiltro("letto", new FiltroPerStato("letto"));

        ArrayList<Libro> risultato = filtroManager.applicaFiltri(libri);

        assertEquals(1, risultato.size());
        assertEquals("Harry Potter", risultato.get(0).getTitolo());
    }

    @Test
    @DisplayName("Filtri che non producono risultati")
    void testFiltriSenzaRisultati() {
        // Fantasy + Da leggere (nessun libro soddisfa entrambi)
        filtroManager.aggiungiFiltro("fantasy", new FiltroPerGenere("fantasy"));
        filtroManager.aggiungiFiltro("da_leggere", new FiltroPerStato("da_leggere"));

        ArrayList<Libro> risultato = filtroManager.applicaFiltri(libri);

        assertTrue(risultato.isEmpty());
    }

    @Test
    @DisplayName("Rimozione filtro")
    void testRimozioneFiltro() {
        filtroManager.aggiungiFiltro("fantasy", new FiltroPerGenere("fantasy"));
        filtroManager.aggiungiFiltro("letto", new FiltroPerStato("letto"));

        // Prima dell'applicazione: 1 risultato
        assertEquals(1, filtroManager.applicaFiltri(libri).size());

        // Rimuovi il filtro per stato
        filtroManager.rimuoviFiltro("letto");

        // Dopo la rimozione: dovrebbe restare solo il filtro fantasy
        assertEquals(1, filtroManager.applicaFiltri(libri).size());
    }

    @Test
    @DisplayName("Pulizia di tutti i filtri")
    void testPulisciFiltri() {
        filtroManager.aggiungiFiltro("fantasy", new FiltroPerGenere("fantasy"));
        filtroManager.aggiungiFiltro("letto", new FiltroPerStato("letto"));

        assertTrue(filtroManager.filtriAttivi());

        filtroManager.pulisciFiltri();

        assertFalse(filtroManager.filtriAttivi());
        assertEquals(libri.size(), filtroManager.applicaFiltri(libri).size());
    }

    @Test
    @DisplayName("Nessun filtro applicato restituisce tutti i libri")
    void testNessunFiltro() {
        ArrayList<Libro> risultato = filtroManager.applicaFiltri(libri);

        assertEquals(libri.size(), risultato.size());
        assertEquals(libri, risultato);
    }
}
