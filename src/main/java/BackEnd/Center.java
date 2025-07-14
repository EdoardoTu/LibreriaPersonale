package BackEnd;

import Observer.InterfacciaObserver;
import Observer.LibreriaSubject;
import Strategy.*;

import java.util.ArrayList;
import java.util.Stack;
import Command.*;

public class Center extends LibreriaSubject {
    private Template reader;
    private String filepath;
    private Stack<Command> storiaComandi = new Stack<>();
    private StrategyConcreto filtroManager;
    private Command aggiungiCommand,
            removeCommand,
            updateCommand;


    public Center() {
        //inputHandler = new inputHandler();
        this.filtroManager = new StrategyConcreto();
    }


    public ArrayList<Libro> getLibri() {
        if (reader == null) {
            System.out.println("Aggiungi prima un libro.");
            return null;
        }
        return reader.readData(this.filepath);
    }
    public void caricaLibri(String filepath) {
        System.out.println(filepath);
        this.reader = new JsonReader(filepath);
        System.out.println(reader.getFilepath());
        this.filepath = filepath;
        filtroManager.pulisciFiltri();
    }

    public void aggiungiLibro(ArrayList<Libro> libri){
        System.out.println("Aggiungi libro.");
        System.out.println(reader.getFilepath());
        aggiungiCommand = new aggiungiCommand(this.reader,libri);
        aggiungiCommand.execute();
        storiaComandi.push(aggiungiCommand);

    }

    public void aggiornaLibro(Libro aggiornato) {
         updateCommand = new AggiornaCommand(reader,aggiornato);
        updateCommand.execute();
        storiaComandi.push(updateCommand);

    }
    public void rimuoviLibro(Libro daRimuovere) {
        removeCommand = new rimuoviCommand(reader,daRimuovere);
        removeCommand.execute();
        storiaComandi.push(removeCommand);

    }
    public void aggiungiObserver(InterfacciaObserver observer) {
        this.addObserver(observer);
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
        this.notifyObservers(libri);
    }

    public void filtroPerGenere(String genere) {
        filtroManager.aggiungiFiltro(genere,new FiltroPerGenere(genere));
    }

    public void filtraPerStato(String stato) {

        filtroManager.aggiungiFiltro(stato,new FiltroPerStato(stato));
    }
    public void applicaFiltri(ArrayList<Libro> libriGUI) {
        ArrayList<Libro> libriFiltrati=filtroManager.applicaFiltri(libriGUI);
        // repositoryLibri.notifyObservers(libriFiltrati);
        this.notifyObservers(libriFiltrati);
    }
    public void rimuoviFiltro(String valore,ArrayList<Libro> libriGUI) {
        filtroManager.rimuoviFiltro(valore);
        System.out.println(filtroManager.filtriAttivi());
        if(filtroManager.filtriAttivi()){
            ArrayList<Libro> libriFiltrati = filtroManager.applicaFiltri(libriGUI);
            //repositoryLibri.notifyObservers(libriFiltrati);
            this.notifyObservers(libriFiltrati);
        } else {
            //repositoryLibri.notifyObservers(libriGUI);
            this.notifyObservers(libriGUI);
        }
        //repositoryLibri.notifyObservers(repositoryLibri.getAll());
    }
    public void rimuoviTuttiFiltri(ArrayList<Libro> libriGUI,boolean flag) {
        filtroManager.pulisciFiltri();
        if(flag)
            //repositoryLibri.notifyObservers(libriGUI);
            this.notifyObservers(libriGUI);
        else
            //repositoryLibri.notifyObservers(getLibri());
            this.notifyObservers(getLibri());
    }

    /*public  static void main(String[] args) {
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
    }*/

}
