package BackEnd;

import Observer.InterfacciaObserver;
import Observer.LibreriaSubject;
import Strategy.FiltroPerGenere;
import Strategy.FiltroPerStato;
import Strategy.StrategyConcreto;
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
    private StrategyConcreto filtroManager;



    public Center() {
        //inputHandler = new inputHandler();
        this.filtroManager = new StrategyConcreto();
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
    public void notificaObserver(ArrayList<Libro> libri) {
        repositoryLibri.notifyObservers(libri);
    }

    public void filtraPerGenere(String genere) {
        filtroManager.aggiungiFiltro(genere,new FiltroPerGenere(genere));
    }

    public void filtraPerStato(String stato) {

        filtroManager.aggiungiFiltro(stato,new FiltroPerStato(stato));
    }
    public ArrayList<Libro> applicaFiltri(ArrayList<Libro> libri) {
        return filtroManager.applicaFiltri(libri);
    }
    public void rimuoviFiltro(String valore) {
        filtroManager.rimuoviFiltro(valore);

    }
    public void rimuoviTuttiFiltri() {
        filtroManager.pulisciFiltri();

    }

    public  static void main(String[] args) {
        Center center = new Center();
        center.caricaLibri("src/main/resources/libri.json");
         ArrayList<Libro> libri = center.getLibri();
        //center.aggiungiLibro();
        //center.filtraPerGenere("fantasy");

        //System.out.println("Libri filtrati per genere fantasy: " + center.applicaFiltri(libri));

        //center.filtraPerStato("da_leggere");
        //System.out.println("Libri filtrati per stato da leggere: " + center.applicaFiltri(libri));
        center.filtraPerGenere("horror");
        center.filtraPerStato("letto");
        System.out.println("Libri filtrati : " + center.applicaFiltri(libri));
        center.rimuoviFiltro("horror");
        System.out.println("Libri filtrati dopo rimozione del filtro per genere horror: " + center.applicaFiltri(libri));
        center.rimuoviTuttiFiltri();
        System.out.println("Libri filtrati : " + center.applicaFiltri(libri));
    }

}
