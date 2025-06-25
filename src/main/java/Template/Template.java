package Template;

import GUI.AggiornaLibroDialog;
import GUI.InputLibriDialog;
import GUI.removeDialog;
import Template.Libro;
import Observer.LibreriaSubject;

import java.util.ArrayList;

public abstract class Template extends LibreriaSubject {
    private String filepath;
    public Template(String filepath) {
        this.filepath = filepath;
    }

    public final void save(String filepath){
        ArrayList<Libro> libriJson = readData(filepath);
        ArrayList<Libro> libri = InputLibriDialog.showBookInputDialog();
        libriJson.addAll(libri);
        writeData(filepath, libriJson);
    }
    public final void update(String filepath) {
        ArrayList<Libro> libriJson = readData(filepath);
        if(AggiornaLibroDialog.updateLibroByTitle(libriJson)) {
            writeData(filepath, libriJson);
        } else {
            System.out.println("Libro non trovato o aggiornamento annullato.");
        }
    }
    public final void delete(String filepath) {
        System.out.println(readData(filepath).toString());
        ArrayList<Libro> libriJson = readData(filepath);
        if(removeDialog.removeLibroByTitle(libriJson)) {
            writeData(filepath, libriJson);
        } else {
            System.out.println("Libro non trovato o rimozione annullata.");
        }
    }
    protected abstract void writeData(String filepath, ArrayList<Libro> libriJson);
    protected abstract ArrayList<Libro> readData(String filepath);
}
