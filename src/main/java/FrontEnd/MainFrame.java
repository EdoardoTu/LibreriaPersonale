package FrontEnd;

import BackEnd.Center;
import Template.Libro;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MainFrame  extends JFrame {
    private JTable tabellaLibri;
    private JTextField searchField;
    private final Center center;
    private File selectedFile;
    private listaLibriGUI libriGUI;
    ArrayList<Libro> risultati = new ArrayList<>();


    public MainFrame() {
        this.center = new Center();
        creaGUI();
    }

    private void creaGUI() {
        JFrame frame = new JFrame("Gestione Libri - Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());

        searchField = new JTextField();
        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(e -> cercaLibri());
        searchField.addActionListener(e -> cercaLibri());
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        JPanel northPanel = new JPanel(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        JButton caricaButton = new JButton("Carica da JSON");
        JButton aggiungiButton = new JButton("Aggiungi");
        JButton modificaButton = new JButton("Modifica");
        JButton rimuoviButton = new JButton("Rimuovi");
        JButton undoButton = new JButton("Undo");


        aggiungiButton.addActionListener(e -> {
            center.aggiungiLibro();
            libriGUI.setLibri(center.getLibri());
        });
        modificaButton.addActionListener(e -> {
            center.aggiornaLibro();
            libriGUI.setLibri(center.getLibri());
        });
        rimuoviButton.addActionListener(e -> {
            center.rimuoviLibro();
            libriGUI.setLibri(center.getLibri());
        });
        caricaButton.addActionListener(e -> {
            fileChooser();
            libriGUI.setLibri(center.getLibri());
        });
        undoButton.addActionListener(e -> {
            center.undo();
            libriGUI.setLibri(center.getLibri());
        });
        menuBar.add(caricaButton);
        menuBar.add(aggiungiButton);
        menuBar.add(modificaButton);
        menuBar.add(rimuoviButton);
        menuBar.add(undoButton);

        northPanel.add(menuBar, BorderLayout.NORTH);
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


private void fileChooser() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.getName().toLowerCase().endsWith(".json") || f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "File JSON (*.json)";
        }
    });

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
        center.notificaObserver(risultati);

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


