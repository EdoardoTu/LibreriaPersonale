package Integration;
import BackEnd.JsonReader;
import BackEnd.Libro;
import Command.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test di Integrazione per Comandi")
public class testCommand {
    @TempDir
    Path tempDir;

    private String filePath;
    private JsonReader reader;
    private ArrayList<Libro> libriIniziali;

    @BeforeEach
    void setUp() throws IOException {
        File file = tempDir.resolve("libri.json").toFile();
        filePath = file.getAbsolutePath();
        reader = new JsonReader(filePath);

        ArrayList<Libro> libriIniziali = new ArrayList<>();
        libriIniziali.add(new Libro("Libro Originale", "Autore", 111, Libro.Genere.fantasy, 5, Libro.Stato.letto));
        reader.writeData(filePath, libriIniziali);
    }

    @Order(1)
    @Test
    @DisplayName("Test execute + undo di aggiungiCommand")
    void testAggiungiCommandEUndo() {
        ArrayList<Libro> libriDaAggiungere = new ArrayList<>();
        libriDaAggiungere.add(new Libro("Nuovo Libro", "Nuovo Autore", 222, Libro.Genere.horror, 4, Libro.Stato.da_leggere));

        Command comando = new aggiungiCommand(reader, libriDaAggiungere);

        comando.execute();


        ArrayList<Libro> dopoExecute = reader.readData(filePath);
        assertEquals(2, dopoExecute.size(), "Il numero di libri dovrebbe essere 2 dopo l'aggiunta.");
        assertTrue(dopoExecute.stream().anyMatch(l -> l.getIsbn() == 222), "Il nuovo libro non è stato trovato.");

        comando.undo();


        ArrayList<Libro> dopoUndo = reader.readData(filePath);
        assertEquals(1, dopoUndo.size(), "Il numero di libri dovrebbe tornare a 1 dopo l'undo.");
        assertEquals("Libro Originale", dopoUndo.get(0).getTitolo(), "Il libro originale dovrebbe essere ripristinato.");
    }
    @Order(2)
    @Test
    @DisplayName("Test execute + undo di rimuoviCommand")
    void testRimuoviCommandEUndo() {

        Libro libroDaRimuovere = reader.readData(filePath).get(0);
        Command comando = new rimuoviCommand(reader, libroDaRimuovere);

        comando.execute();


        ArrayList<Libro> dopoExecute = reader.readData(filePath);
        assertTrue(dopoExecute.isEmpty(), "La lista dei libri dovrebbe essere vuota dopo la rimozione.");


        comando.undo();

        ArrayList<Libro> dopoUndo = reader.readData(filePath);
        assertEquals(1, dopoUndo.size(), "La lista dovrebbe contenere nuovamente 1 libro dopo l'undo.");
        assertEquals(111, dopoUndo.get(0).getIsbn(), "Il libro originale dovrebbe essere stato ripristinato.");
    }

    @Order(3)
    @Test
    @DisplayName("Test execute + undo di aggiornaCommand")
    void testAggiornaCommandEUndo() {

        Libro libroOriginale = reader.readData(filePath).get(0);
        Libro libroAggiornato = new Libro("Titolo Modificato", "Autore Originale", 111, Libro.Genere.fantasy, 3, Libro.Stato.letto);

        Command comando = new AggiornaCommand(reader, libroAggiornato);

        comando.execute();


        Libro libroDopoExecute = reader.readData(filePath).get(0);
        assertEquals("Titolo Modificato", libroDopoExecute.getTitolo(), "Il titolo del libro avrebbe dovuto essere aggiornato.");
        assertEquals(3, libroDopoExecute.getValutazione(), "La valutazione avrebbe dovuto essere aggiornata.");
        assertNotEquals(libroOriginale, libroDopoExecute, "Il libro dopo l'esecuzione non dovrebbe essere uguale all'originale.");

        comando.undo();

        Libro libroDopoUndo = reader.readData(filePath).get(0);
        assertEquals(libroOriginale, libroDopoUndo, "Il libro dopo l'undo dovrebbe essere identico all'originale.");
        assertEquals("Libro Originale", libroDopoUndo.getTitolo(), "Il titolo originale avrebbe dovuto essere ripristinato.");
    }
}
