package FrontEnd;

import Template.Libro;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AggiornaLibroDialog {
    public static Libro updateLibroByTitle(ArrayList<Libro> libri) {
        if (libri.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Nessun libro presente nel sistema!",
                    "Errore",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // 1. Selezione libro per TITOLO (con controllo duplicati)
        List<String> titoliUnici = libri.stream()
                .map(Libro::getTitolo)
                .distinct()
                .collect(Collectors.toList());

        String selectedTitle = (String) JOptionPane.showInputDialog(
                null,
                "Seleziona il titolo del libro da modificare:",
                "Modifica Libro",
                JOptionPane.QUESTION_MESSAGE,
                null,
                titoliUnici.toArray(),
                titoliUnici.get(0)
        );

        if (selectedTitle == null) return null;

        List<Libro> libriCorrispondenti = libri.stream()
                .filter(l -> l.getTitolo().equals(selectedTitle))
                .collect(Collectors.toList());

        Libro libroToUpdate;

        if (libriCorrispondenti.size() == 1) {
            libroToUpdate = libriCorrispondenti.get(0);
        } else {
            // Se ci sono più libri, falli scegliere da una tabella
            String[] columnNames = {"Titolo", "Autore", "ISBN"};
            Object[][] data = libriCorrispondenti.stream()
                    .map(l -> new Object[]{l.getTitolo(), l.getAutore(), l.getIsbn()})
                    .toArray(Object[][]::new);

            JTable table = new JTable(data, columnNames);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            int result = JOptionPane.showConfirmDialog(null, new JScrollPane(table),
                    "Seleziona il libro da modificare", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    libroToUpdate = libriCorrispondenti.get(selectedRow);
                } else {
                    return null; // Nessuna selezione
                }
            } else {
                return null; // Annullato
            }
        }


        // 3. Selezione attributo da modificare
        String[] attributi = {"titolo", "autore", "genere", "valutazione", "stato"};
        String attributo = (String) JOptionPane.showInputDialog(
                null,
                "Seleziona attributo da modificare:",
                "Modifica Attributo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                attributi,
                attributi[0]
        );

        if (attributo == null) return null;

        // 4. Input nuovo valore con validazione
        try {
            switch (attributo.toLowerCase()) {
                case "titolo":
                    String newTitolo = JOptionPane.showInputDialog("Nuovo titolo:", libroToUpdate.getTitolo());
                    if (newTitolo == null || newTitolo.trim().isEmpty() || newTitolo.matches(".*\\d.*")) {
                        throw new IllegalArgumentException("Titolo non valido!");
                    }
                    libroToUpdate.setTitolo(newTitolo);
                    break;

                case "autore":
                    String newAutore = JOptionPane.showInputDialog("Nuovo autore:", libroToUpdate.getAutore());
                    if (newAutore == null || newAutore.trim().isEmpty() || newAutore.matches(".*\\d.*")) {
                        throw new IllegalArgumentException("Autore non valido!");
                    }
                    libroToUpdate.setAutore(newAutore);
                    break;

                case "genere":
                    Libro.Genere newGenere = (Libro.Genere) JOptionPane.showInputDialog(
                            null,
                            "Seleziona genere:",
                            "Modifica Genere",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            Libro.Genere.values(),
                            libroToUpdate.getGenere()
                    );
                    if (newGenere != null) {
                        libroToUpdate.setGenere(newGenere);
                    }
                    break;

                case "valutazione":
                    JSpinner spinner = new JSpinner(new SpinnerNumberModel(
                            libroToUpdate.getValutazione(), 1, 5, 1));
                    JOptionPane.showMessageDialog(null, spinner, "Nuova valutazione (1-5):", JOptionPane.QUESTION_MESSAGE);
                    libroToUpdate.setValutazione((int) spinner.getValue());
                    break;

                case "stato":
                    Libro.Stato newStato = (Libro.Stato) JOptionPane.showInputDialog(
                            null,
                            "Seleziona stato:",
                            "Modifica Stato",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            Libro.Stato.values(),
                            libroToUpdate.getStato()
                    );
                    if (newStato != null) {
                        libroToUpdate.setStato(newStato);
                    }
                    break;
            }
            JOptionPane.showMessageDialog(null,
                    "Libro aggiornato con successo!",
                    "Successo",
                    JOptionPane.INFORMATION_MESSAGE);
            return libroToUpdate;

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
