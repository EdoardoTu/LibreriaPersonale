package BackEnd;

import Observer.InterfacciaObserver;
import Observer.LibreriaSubject;
import Template.Libro;
import Template.Repository2;

import java.util.ArrayList;
import java.util.Stack;
import Command.*;

public class Center extends LibreriaSubject {
    private static Center instance;
    private Repository2 repositoryLibri;
    public static inputHandler inputHandler ;
    private String filepath;
    private Stack<Command> storiaComandi = new Stack<>();



    private Center() {
        inputHandler = new inputHandler();
    }
     public static Center getInstance() {
        if (instance == null) {
            instance = new Center();
        }
        return instance;
    }
    public void caricaLibri(String filepath) {
        this.repositoryLibri = new Repository2(filepath);
        this.filepath = filepath;
    }

    public void aggiungiLibro(){
        Command aggiungiCommand = new aggiungiCommand(repositoryLibri);
        aggiungiCommand.execute();
        storiaComandi.push(aggiungiCommand);
        //chiamata da GUI TO DO
    }

    public void aggiornaLibro() {
        Command updateCommand = new AggiornaCommand(repositoryLibri);
        updateCommand.execute();
        storiaComandi.push(updateCommand);
        //chiamata da GUI TO DO
    }
    public void rimuoviLibro() {
        Command removeCommand = new rimuoviCommand(repositoryLibri);
        removeCommand.execute();
        storiaComandi.push(removeCommand);
        //chiamata da GUI TO DO
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
