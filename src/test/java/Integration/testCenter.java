package Integration;

import BackEnd.Center;
import BackEnd.Libro;
import Observer.InterfacciaObserver;
import BackEnd.JsonReader;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;


@DisplayName("Test di Integrazione per GestionLibreria")
public class testCenter {
    @TempDir
    Path tempDir;


    private Center center;
    private ArrayList<Libro> libriTest;
    String filePath;
    private JsonReader reader;

    @BeforeEach
    void setUp() {

        File file = tempDir.resolve("libri.json").toFile();
        filePath = file.getAbsolutePath();
        //center = new Center();
        libriTest = new ArrayList<>();
        libriTest.add(new Libro("Libro1", "Autore1", 1,
                Libro.Genere.fantasy, 5, Libro.Stato.letto));
        libriTest.add(new Libro("Libro2", "Autore2", 2,
                Libro.Genere.horror, 4, Libro.Stato.da_leggere));

        reader = new JsonReader(filePath);
        reader.writeData(filePath,libriTest);

        center = new Center();

        center.caricaLibri(filePath);

    }


    @Test
    @DisplayName("Gestione filtri con notifica Observer")
    void testFiltriConObserver() {

        osservatoreTest observer = new osservatoreTest();
        center.aggiungiObserver(observer);


        center.filtroPerGenere("fantasy");
        center.applicaFiltri(libriTest);


        assertTrue(observer.notificato());
        System.out.println(observer.getUltimiLibri().toString());
        assertEquals(1, observer.getUltimiLibri().size());
    }


    private static class osservatoreTest implements InterfacciaObserver {
        private boolean notified = false;
        private ArrayList<Libro> lastReceivedBooks;

        @Override
        public void update(ArrayList<Libro> libri) {
            notified = true;
            lastReceivedBooks = libri;
        }

        public boolean notificato() {
            return notified;
        }

        public ArrayList<Libro> getUltimiLibri() {
            return lastReceivedBooks;
        }
    }
}
