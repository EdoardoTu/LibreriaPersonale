package BackEnd;

import Observer.InterfacciaObserver;
import Observer.LibreriaSubject;
import Template.Libro;
import Template.Repository2;

import java.util.ArrayList;
import java.util.Stack;
import Command.*;

public class Center extends LibreriaSubject {
    private Repository2 repositoryLibri;
    public static inputHandler inputHandler ;
    private String filepath;
    private Stack<Command> storiaComandi = new Stack<>();



    public Center() {
        inputHandler = new inputHandler();
    }


    public ArrayList<Libro> getLibri() {
        if (repositoryLibri == null) {
            System.out.println("Aggiungi prima un libro.");
            return null;
        }
        return repositoryLibri.readData(this.filepath);
    }
    public void caricaLibri(String filepath) {
        System.out.println(filepath);
        this.repositoryLibri = new Repository2(filepath);
        System.out.println(repositoryLibri.getFilepath());
        this.filepath = filepath;
    }

    public void aggiungiLibro(){
        System.out.println("Aggiungi libro.");
        System.out.println(repositoryLibri.getFilepath());
        Command aggiungiCommand = new aggiungiCommand(this.repositoryLibri);
        aggiungiCommand.execute();
        storiaComandi.push(aggiungiCommand);

    }

    public void aggiornaLibro() {
        Command updateCommand = new AggiornaCommand(repositoryLibri);
        updateCommand.execute();
        storiaComandi.push(updateCommand);

    }
    public void rimuoviLibro() {
        Command removeCommand = new rimuoviCommand(repositoryLibri);
        removeCommand.execute();
        storiaComandi.push(removeCommand);

    }
    public void aggiungiObserver(InterfacciaObserver observer) {
        repositoryLibri.addObserver(observer);
    }

    public void undo(){
        if (!storiaComandi.isEmpty()) {
            System.out.println(storiaComandi.toString());
            Command ultimoComando = storiaComandi.pop();
            ultimoComando.undo();
            System.out.println("Ultimo comando annullato.");
        } else {
            System.out.println("Nessun comando da annullare.");
        }
    }
}
