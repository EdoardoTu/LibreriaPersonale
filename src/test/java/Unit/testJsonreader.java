package Unit;

import Template.*;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

@DisplayName("Test di Integrazione Repository")
public class testJsonreader {
    @TempDir
    Path tempDir;

    private JsonReader jsonReader;
    private String testFilePath;
    private ArrayList<Libro> libriTest;
    private Gson gson = new Gson();

    @BeforeEach
    void setUp() throws IOException {
        // Crea file temporaneo
        File tempFile = tempDir.resolve("test_integration.json").toFile();
        testFilePath = tempFile.getAbsolutePath();

        // Inizializza repository
        jsonReader = new JsonReader(testFilePath);
        creaFileJsonTest();

    }
    private void creaFileJsonTest() throws IOException {
        String jsonContent = """
            [
                {
                    "titolo": "Il Signore degli Anelli",
                    "autore": "Tolkien",
                    "isbn": 111,
                    "genere": "fantasy",
                    "valutazione": 5,
                    "stato": "letto"
                },
                {
                    "titolo": "Dracula",
                    "autore": "Stoker",
                    "isbn": 222,
                    "genere": "horror",
                    "valutazione": 4,
                    "stato": "da_leggere"
                }
            ]
            """;

        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write(jsonContent);
        }
    }

    @Test
    @DisplayName("Lettura dati da file JSON")
    void testReadDataFromJson() {
        // Act
        ArrayList<Libro> result = jsonReader.readData(testFilePath);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        Libro primo = result.get(0);
        System.out.println(primo);
        assertEquals("Il Signore degli Anelli", primo.getTitolo());
        assertEquals("Tolkien", primo.getAutore());
        assertEquals(111, primo.getIsbn());
        assertEquals("fantasy", primo.getGenere());
        assertEquals(5, primo.getValutazione());
        assertEquals("letto", primo.getStato());
    }
    @Test
    @DisplayName("Scrittura dati su file JSON")
    void testWriteDataToJson() throws IOException {
        // Arrange - Nuovo file per test scrittura
        File nuovoFile = tempDir.resolve("test_write.json").toFile();
        String nuovoPath = nuovoFile.getAbsolutePath();

        ArrayList<Libro> nuoviLibri = new ArrayList<>();
        nuoviLibri.add(new Libro("1984", "Orwell", 333,
                Libro.Genere.fantascienza, 5, Libro.Stato.in_lettura));

        // Act
        jsonReader.writeData(nuovoPath, nuoviLibri);

        // Assert - Rileggi il file per verificare

        ArrayList<Libro> letti = jsonReader.readData(nuovoPath);
        System.out.println(letti.toString());
        assertNotNull(letti);
        assertEquals(1, letti.size());
        assertEquals("1984", letti.get(0).getTitolo());
    }
}
