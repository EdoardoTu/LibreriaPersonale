package BackEnd;

import Template.Libro;
import Template.Repository2;

import java.util.ArrayList;
import java.util.Stack;
import Command.*;

public class Center {
    private static Center instance;
    private Repository2 repositoryLibri;
    public static inputHandler inputHandler ;
    private String filepath;



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
        //chiamata da GUI TO DO
    }

    public void aggiornaLibro() {
        Command updateCommand = new AggiornaCommand(repositoryLibri);
        updateCommand.execute();
        //chiamata da GUI TO DO
    }
    public void rimuoviLibro() {
        Command removeCommand = new rimuoviCommand(repositoryLibri);
        removeCommand.execute();
        //chiamata da GUI TO DO
    }

    /*public static void main(String[] args) {
        System.out.println("Benvenuto nella libreria");
        Center g = getInstance();
        g.caricaLibri("libri.json");
        boolean continua = true;
        while(continua) {
            System.out.println("1. Aggiungi libro");
            System.out.println("2. Rimuovi libro");
            System.out.println("3. Aggiorna libro");
            System.out.println("4. Esci");
            int scelta = inputHandler.leggiInteroRange("Inserisci la tua scelta: ", 1, 4);
            switch (scelta) {
                case 1:
                    g.aggiungiLibro();
                    break;
                case 2:
                    g.rimuoviLibro();
                    break;
                case 3:
                    g.aggiornaLibro();
                    break;
                case 4:
                    continua = false;
                    break;
            }
        }
    }*/
}
