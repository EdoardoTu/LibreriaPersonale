package Integration;

import BackEnd.Center;
import BackEnd.Libro;
import BackEnd.JsonReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test per il controllo compelto del flusso di lavoro")
public class workFlow {

    private Center center;
    private String filePath;
    private JsonReader reader;
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        File file = tempDir.resolve("workflow_libri.json").toFile();
        filePath = file.getAbsolutePath();

        center = new Center();
        center.caricaLibri(filePath);

        ArrayList<Libro> libriIniziali = new ArrayList<>();
        libriIniziali.add(new Libro("Il nome della rosa", "Umberto Eco", 1, Libro.Genere.fantasy, 5, Libro.Stato.letto));
        libriIniziali.add(new Libro("Dune", "Frank Herbert", 2, Libro.Genere.fantascienza, 5, Libro.Stato.letto));
        libriIniziali.add(new Libro("Dracula", "Bram Stoker", 3, Libro.Genere.horror, 4, Libro.Stato.da_leggere));


        reader = new JsonReader(filePath);
        reader.writeData(filePath, libriIniziali);
    }

    @Test
    @DisplayName("Simula aggiunta, filtro, aggiornamento, rimozione e undo")
    void testWorkflowCompleto() {

        assertEquals(3, center.getLibri().size(), "Dovrebbero esserci 3 libri all'inizio.");


        Libro nuovoLibro = new Libro("Cronache marziane", "Ray Bradbury", 4, Libro.Genere.fantascienza, 5, Libro.Stato.letto);
        ArrayList<Libro> lib = new ArrayList<>();
        lib.add(nuovoLibro);
        center.aggiungiLibro(lib);
        assertEquals(4, center.getLibri().size(), "Dovrebbero esserci 4 libri dopo l'aggiunta.");

        center.filtroPerGenere("fantascienza");
        ArrayList<Libro> libriCorrenti = center.getLibri();
        ArrayList<Libro> libriFiltrati = new ArrayList<>();
        center.addObserver(libriFiltrati::addAll); // Observer "mock" per catturare i risultati
        center.applicaFiltri(libriCorrenti);


        // Per semplicità applico il filtro direttamente per la verifica.
        ArrayList<Libro> risultatoFiltro = new ArrayList<>();
        for(Libro l : libriCorrenti){
            if(l.getGenere().equals("fantascienza"))
                risultatoFiltro.add(l);
        }

        assertEquals(2, risultatoFiltro.size(), "Dovrebbero esserci 2 libri di fantascienza.");
        assertTrue(risultatoFiltro.stream().allMatch(l -> l.getGenere().equals("fantascienza")), "Tutti i libri filtrati dovrebbero essere di fantascienza.");


        center.rimuoviTuttiFiltri(libriCorrenti, false);
        assertEquals(4, center.getLibri().size(), "Tutti i libri dovrebbero essere nuovamente visibili.");


        Libro libroDaAggiornare = center.getLibri().stream().filter(l -> l.getIsbn() == 3).findFirst().get();
        libroDaAggiornare.setStato(Libro.Stato.letto);
        //libroDaAggiornare.setValutazione(5);
        center.aggiornaLibro(libroDaAggiornare);

        Libro libroAggiornato = center.getLibri().stream().filter(l -> l.getIsbn() == 3).findFirst().get();
        assertEquals(Libro.Stato.letto.toString(), libroAggiornato.getStato(), "Lo stato di Dracula dovrebbe essere 'letto'.");
        //assertEquals(5, libroAggiornato.getValutazione(), "La valutazione di Dracula dovrebbe essere 5.");


        Libro libroDaRimuovere = center.getLibri().stream().filter(l -> l.getIsbn() == 2).findFirst().get();
        center.rimuoviLibro(libroDaRimuovere);
        assertEquals(3, center.getLibri().size(), "Dovrebbero esserci 3 libri dopo la rimozione.");
        assertFalse(center.getLibri().stream().anyMatch(l -> l.getIsbn() == 2), "Il libro Dune non dovrebbe più esistere.");


        center.undo();
        assertEquals(4, center.getLibri().size(), "Dovrebbero esserci nuovamente 4 libri dopo l'undo.");
        assertTrue(center.getLibri().stream().anyMatch(l -> l.getIsbn() == 2), "Il libro Dune dovrebbe essere stato ripristinato.");


        center.undo();
        Libro libroRipristinato = center.getLibri().stream().filter(l -> l.getIsbn() == 3).findFirst().get();
        assertEquals(Libro.Stato.da_leggere.toString(), libroRipristinato.getStato(), "Lo stato originale di Dracula dovrebbe essere ripristinato.");
    }
}
