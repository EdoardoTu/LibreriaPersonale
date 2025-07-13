package FrontEnd;

import BackEnd.Center;
import Template.Libro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame  extends JFrame {
    private JTable tabellaLibri;
    private JTextField searchField;
    private final Center center;
    private File selectedFile;
    private listaLibriGUI libriGUI;
    ArrayList<Libro> risultati = new ArrayList<>();
    MenuComposite statoMenu;
    MenuComposite genereMenu;


    public MainFrame() {
        this.center = new Center();
        creaGUI();
    }

    private void creaGUI() {
        JFrame frame = new JFrame("Gestione Libri - Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        //libriCaricati = new ArrayList<>();

        // Crea il pannello principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(e -> cercaLibri());
        searchField.addActionListener(e -> cercaLibri());

        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(menuComposite(), BorderLayout.NORTH);
        northPanel.add(topPanel, BorderLayout.SOUTH);
        mainPanel.add(northPanel, BorderLayout.NORTH);

        libriGUI = new listaLibriGUI(new ArrayList<>());
        this.tabellaLibri = new JTable(libriGUI); 
        this.tabellaLibri.setAutoCreateRowSorter(true);
        this.tabellaLibri.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(this.tabellaLibri);
        scrollPane.setPreferredSize(new Dimension(1100, 700));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JMenuBar menuComposite() {
        JMenuBar menuBar = new JMenuBar();
        MenuComposite fileMenu = new MenuComposite("File");
        MenuComposite editMenu = new MenuComposite("Modifica");
        MenuComposite filterMenu = new MenuComposite("Filtri");

        genereMenu = new MenuComposite("Per Genere");
        statoMenu = new MenuComposite("Per Stato");

        fileMenu.aggiungi(new MenuItem("Carica da JSON", e -> {
            fileChooser();
            libriGUI.setLibri(center.getLibri());
            rimuoviFiltri(genereMenu, statoMenu);
            searchField.setText("");
        }));


        editMenu.aggiungi(new MenuItem("Aggiungi Libro", e -> {
            ArrayList<Libro> libri = InputLibriDialog.showBookInputDialog();
            center.aggiungiLibro(libri);
            rimuoviFiltri(genereMenu, statoMenu);
            searchField.setText("");
            //libriGUI.setLibri(gestionLibreria.getLibri());
        }));
        editMenu.aggiungi(new MenuItem("Modifica Libro", e -> {
            Libro l = AggiornaLibroDialog.updateLibroByTitle(center.getLibri());
            center.aggiornaLibro(l);
            rimuoviFiltri(genereMenu, statoMenu);
            searchField.setText("");
            //libriGUI.setLibri(gestionLibreria.getLibri());

        }));
        editMenu.aggiungi(new MenuItem("Rimuovi Libro", e -> {
            Libro l = removeDialog.showRemoveDialog(center.getLibri());
            center.rimuoviLibro(l);
            rimuoviFiltri(genereMenu, statoMenu);
            searchField.setText("");
            //libriGUI.setLibri(gestionLibreria.getLibri());
        }));
        editMenu.aggiungi(new MenuItem("Annulla ultima operazione", e -> {
            center.undo();
            rimuoviFiltri(genereMenu, statoMenu);
            searchField.setText("");
            //libriGUI.setLibri(gestionLibreria.getLibri());
        }));
        // Sottomenu per genere
        for (Libro.Genere g : Libro.Genere.values()) {
            genereMenu.aggiungi(createFilterCheckboxItem(g.name().toLowerCase(),
                    g.name(), "genere"));
        }

        // Sottomenu per stato
        for (Libro.Stato s : Libro.Stato.values()) {
            String statoName = s.name().toLowerCase().replace("_", " ");
            statoMenu.aggiungi(createFilterCheckboxItem(statoName, s.name(), "stato"));
        }

        filterMenu.aggiungi(genereMenu);
        filterMenu.aggiungi(statoMenu);

        filterMenu.aggiungi(new MenuItem("Pulisci filtri", e -> rimuoviFiltri(genereMenu, statoMenu)));

        // Costruisci la menu bar
        menuBar.add(fileMenu.build());
        menuBar.add(editMenu.build());
        menuBar.add(filterMenu.build());

        return menuBar;
    }

    private Component createFilterCheckboxItem(String displayName, String filterValue, String filterType) {
        ActionListener listener = e -> {
            JCheckBoxMenuItem source = (JCheckBoxMenuItem) e.getSource();
            if (source.isSelected()) {
                if ("genere".equals(filterType)) {
                    center.filtroPerGenere(filterValue);
                } else {
                    center.filtraPerStato(filterValue);
                }
                // searchField.setText(""); // Pulisce il campo di ricerca quando si applica un filtro
                if(!risultati.isEmpty()){
                    center.applicaFiltri(risultati);
                }
                else
                    center.applicaFiltri(libriGUI.getLibri());
            } else if(!risultati.isEmpty()){
                center.rimuoviFiltro(filterValue, risultati);
            }
            else center.rimuoviFiltro(filterValue, center.getLibri());
        };

        /*return new MenuItem(displayName, listener) {
            @Override
            public JComponent build() {
                JCheckBoxMenuItem item = new JCheckBoxMenuItem(nome);
                item.addActionListener(listener); // Usiamo la variabile locale
                return item;
            }
        };*/
        return new SubItem(displayName, listener);

    }


private void fileChooser() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter(
            "File JSON (*.json)", "json");
    fileChooser.setFileFilter(jsonFilter);
    fileChooser.setAcceptAllFileFilterUsed(false);
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        selectedFile = fileChooser.getSelectedFile();
        JOptionPane.showMessageDialog(this, "File selezionato: " + selectedFile.getAbsolutePath());

        try {
            center.caricaLibri(selectedFile.getAbsolutePath());
            System.out.println(selectedFile.getAbsolutePath());
            center.aggiungiObserver(libriGUI);
            //libriCaricati = gestionLibreria.getLibri();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore durante il caricamento del file: " + ex.getMessage());
        }
    }
    }

    private void cercaLibri() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            libriGUI.setLibri(center.getLibri());
        }

        risultati.clear();
        for (Libro libro : center.getLibri()) {
            if (libro.getTitolo().toLowerCase().contains(searchText) ||
                    libro.getAutore().toLowerCase().contains(searchText)) {
                risultati.add(libro);
            }
        }
        libriGUI.setLibri(risultati);
        System.out.println(risultati);
        center.notifyObservers(risultati);

    }
    private void rimuoviFiltri(MenuComposite genereMenu, MenuComposite statoMenu) {
        System.out.println("Rimuovo i filtri");

        for (int i = 0; i < genereMenu.getItemCount(); i++) {
            ((SubItem) genereMenu.getChild(i)).setFalse();
        }
        for (int i = 0; i < statoMenu.getItemCount(); i++) {
            ((SubItem) statoMenu.getChild(i)).setFalse();
        }
        System.out.println("Rimuovo i filtri");
        if (risultati.isEmpty()) {
            center.rimuoviTuttiFiltri(center.getLibri(),false);
        } else {
            center.rimuoviTuttiFiltri(risultati,true);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}


