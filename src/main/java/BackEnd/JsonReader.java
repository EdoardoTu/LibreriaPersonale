package BackEnd;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonReader extends Template {
    private final Gson gson = new Gson();
    //private final LibreriaSubject library = new LibreriaSubject();
    private final String filepath;
    public JsonReader(String filepath) {
        super(filepath);
        this.filepath = filepath;
    }
    @Override
    public void writeData(String filepath, ArrayList<Libro> libri) {
        try (FileWriter filewriter = new FileWriter(filepath)){

            gson.toJson(libri, filewriter);
            notifyObservers(libri);
            filewriter.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    @Override
    public ArrayList<Libro> readData(String filepath) {
        try (FileReader fileReader = new FileReader(filepath);){

            Type arrayType = new TypeToken<ArrayList<Libro>>() {
            }.getType();
            ArrayList<Libro> libriJson = gson.fromJson(fileReader, arrayType);
            if (libriJson == null) {
                System.out.println("Nessun libro trovato, creo un nuovo file");
                return null;
            } else {
                //System.out.println(getObservers());
                notifyObservers(libriJson);
                return libriJson;
            }
        } catch (IOException e) {
            throw new RuntimeException();

        }
    }


}
