package BackEnd;

import Template.Libro;
import Template.Repository2;

import java.util.ArrayList;
import java.util.Stack;

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
        ArrayList<Libro> libri = new ArrayList<>();
        boolean continua = true;
        while(continua) {
            String titolo = inputHandler.leggiStringa("Inserisci titolo: ");
            String autore = inputHandler.leggiStringa("Inserisci autore: ");
            int isbn = inputHandler.leggiIntero("Inserisci isbn: ");
            String genereInput = inputHandler.leggiStringa("Inserisci genere: ").toUpperCase();
            Libro.Genere genere;

            try {
                genere = Libro.Genere.valueOf(genereInput.toLowerCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Genere non valido. Impostato su ALTRO.");
                genere = Libro.Genere.altro;
            }
            String statoInput = inputHandler.leggiStringa("Inserisci stato lettura: ").toUpperCase().replace(" ", "_");
            Libro.Stato stato;

            try {
                stato = Libro.Stato.valueOf(statoInput.toLowerCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Stato non valido. Impostato su DA_LEGGERE.");
                stato = Libro.Stato.da_leggere;
            }
            int valutazione = inputHandler.leggiInteroRange("Inserisci valutazione (1-5): ", 1, 5);
            Libro l = new Libro(titolo, autore, isbn, genere, valutazione, stato);
            libri.add(l);

            String risposta = inputHandler.leggiStringa("Vuoi continuare? (si/no)");
            if(risposta.equals("no")) {
                continua = false;
            }
        }

        //chiamata da GUI TO DO
    }

    public void aggiornaLibro() {
        ArrayList<Libro> libriJson = repositoryLibri.readData();
        boolean modificato = false;
        int isbn = inputHandler.leggiIntero("Inserisci l'ISBN del libro da aggiornare: ");
        for (Libro l : libriJson) {
            if (l.getIsbn() == isbn) {
                String attributo = null;
                boolean continua = false;
                while(!continua){
                    String input = inputHandler.leggiStringa("Inserisci l'attributo da aggiornare (titolo, autore, genere, valutazione): ");
                    switch (input.toLowerCase()) {
                        case "titolo":
                            attributo = input;
                            continua = true;
                            break;
                        case "autore":
                            attributo = input;
                            continua = true;
                            break;
                        case "genere":
                            attributo = input;
                            continua = true;
                            break;
                        case "valutazione":
                            attributo = input;
                            continua = true;
                            break;
                        case "stato":
                            attributo = input;
                            continua = true;
                            break;
                        default:
                            System.out.println("Attributo non valido");
                    }
                }
                Object nuovoValore = null;
                if(attributo.equals("titolo") || attributo.equals("autore")) {
                    nuovoValore = inputHandler.leggiStringa("Inserisci il nuovo valore: ");
                }else if(attributo.equals("valutazione")) {
                    nuovoValore = inputHandler.leggiInteroRange("Inserisci la nuova valutazione: ", 1, 5);

                }
                else if(attributo.equals("genere")) {
                    String genereInput = inputHandler.leggiStringa("Inserisci il nuovo genere: ").toUpperCase();
                    Libro.Genere genere;

                    try {
                        genere = Libro.Genere.valueOf(genereInput.toLowerCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Genere non valido. Impostato su ALTRO.");
                        genere = Libro.Genere.altro;
                    }
                    nuovoValore = genere;
                }
                else if(attributo.equals("stato")) {
                    String statoInput = inputHandler.leggiStringa("Inserisci il nuovo stato: ").toUpperCase().replace(" ", "_");
                    Libro.Stato stato;

                    try {
                        stato = Libro.Stato.valueOf(statoInput.toLowerCase());
                        nuovoValore = stato;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Stato non valido. Impostato su DA_LEGGERE.");
                        stato = Libro.Stato.da_leggere;
                    }

                }
                else{
                    System.out.println("Attributo non valido");
                }
                System.out.println("attributo valido" );
                switch (attributo) {
                    case "titolo" : l.setTitolo((String) nuovoValore);
                        System.out.println("Titolo aggiornato");
                        modificato=true;
                        break;
                    case "autore" : l.setAutore((String) nuovoValore);
                        modificato=true;
                        break;
                    case "isbn" : l.setIsbn((int) nuovoValore);
                        modificato=true;
                        break;
                    case "genere" :l.setGenere((Libro.Genere) nuovoValore);
                        modificato=true;
                        break;
                    case "valutazione": l.setValutazione((int) nuovoValore);
                        modificato=true;
                        break;
                    case "stato" : l.setStato((Libro.Stato) nuovoValore);
                        modificato=true;
                        break;
                }

            }


        }
        //chiamata da GUI TO DO
    }
    public void rimuoviLibro() {
        ArrayList<Libro> libriJson = repositoryLibri.readData();
        int isbn = inputHandler.leggiIntero("Inserisci l'ISBN del libro da rimuovere ");
        boolean rimosso = false;
        for(Libro l : libriJson) {
            if(l.getIsbn() == isbn) {
                libriJson.remove(l);
                rimosso = true;
                break;
            }

        }
        //chiamata da GUI TO DO
    }

    public static void main(String[] args) {
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
    }
}
