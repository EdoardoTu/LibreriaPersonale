package Unit;
import FrontEnd.listaLibriGUI;
import BackEnd.Libro;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
@DisplayName("Test per listaLibriGUI come Observer")
public class Observer {
    private listaLibriGUI libriGUI;
    private ArrayList<Libro> libriIniziali;

    @BeforeEach
    void setUp() {

        libriIniziali = new ArrayList<>();
        libriIniziali.add(new Libro("Libro Iniziale", "Autore", 1, Libro.Genere.giallo, 4, Libro.Stato.letto));

        libriGUI = new listaLibriGUI(new ArrayList<>());
    }
    @Test
    @DisplayName("Aggiornamento dati nella tabella")
    void testUpdateChangesData() {
        libriGUI.update(libriIniziali);


        assertEquals(1, libriGUI.getRowCount(), "La tabella dovrebbe avere una riga dopo l'aggiornamento.");
        assertEquals("Libro Iniziale", libriGUI.getValueAt(0, 0), "Il titolo del libro non è corretto.");
    }
    @Test
    @DisplayName("3. Aggiornamenti Multipli Sovrascrivono i Dati")
    void testMultipleUpdatesOverwriteData() {

        libriGUI.update(libriIniziali);
        assertEquals(1, libriGUI.getRowCount(), "La tabella dovrebbe avere una riga dopo il primo aggiornamento.");

        ArrayList<Libro> libriNuovi = new ArrayList<>();
        libriNuovi.add(new Libro("Nuovo Libro 1", "Nuovo Autore", 101, Libro.Genere.fantasy, 5, Libro.Stato.letto));
        libriNuovi.add(new Libro("Nuovo Libro 2", "Nuovo Autore", 102, Libro.Genere.horror, 4, Libro.Stato.in_lettura));

        // Act 2: Secondo aggiornamento
        libriGUI.update(libriNuovi);

        assertEquals(2, libriGUI.getRowCount(), "La tabella dovrebbe avere due righe dopo il secondo aggiornamento.");
        assertEquals("Nuovo Libro 1", libriGUI.getValueAt(0, 0), "Il primo libro della nuova lista non è corretto.");
        assertEquals("Nuovo Libro 2", libriGUI.getValueAt(1, 0), "Il secondo libro della nuova lista non è corretto.");
    }

}
