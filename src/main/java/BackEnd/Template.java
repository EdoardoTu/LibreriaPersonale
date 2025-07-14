package BackEnd;

import Observer.LibreriaSubject;

import java.util.ArrayList;

public abstract class Template extends LibreriaSubject {
    private String filepath;
    public Template(String filepath) {
        this.filepath = filepath;
    }
    public String getFilepath(){

        return filepath;
    }

    public final void save(String filepath, ArrayList<Libro> libri) {
        ArrayList<Libro> libriJson = readData(filepath);
        libriJson.addAll(libri);
        writeData(filepath, libriJson);
    }
    public final void update(String filepath,Libro libroAggiornato) {
        ArrayList<Libro> libriJson = readData(filepath);
        if(libriJson!= null){
            for (int i = 0; i < libriJson.size(); i++) {
                if (libriJson.get(i).getIsbn() == libroAggiornato.getIsbn()) {
                    libriJson.set(i, libroAggiornato);
                    writeData(filepath, libriJson);
                    return;
                }
            }
        }
    }
    public final void delete(String filepath, Libro libroDaRimuovere) {
        ArrayList<Libro> libriAttuali = readData(filepath);
        libriAttuali.removeIf(libro -> libro.getIsbn() == libroDaRimuovere.getIsbn());
        writeData(filepath,libriAttuali);
        System.out.println("Libro non trovato o rimozione annullata.");

    }

    public abstract void writeData(String filepath, ArrayList<Libro> libriJson);

    public abstract ArrayList<Libro> readData(String filepath);
}