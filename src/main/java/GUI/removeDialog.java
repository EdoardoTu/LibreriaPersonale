package GUI;
import Template.Libro;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class removeDialog {
    public static boolean removeLibroByTitle(ArrayList<Libro> libri) {
        if (libri.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Nessun libro presente nel sistema!",
                    "Errore",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 1. Selezione libro per TITOLO
        List<String> titoliUnici = libri.stream()
                .map(Libro::getTitolo)
                .distinct()
                .toList();

        String selectedTitle = (String) JOptionPane.showInputDialog(
                null,
                "Seleziona il titolo del libro da rimuovere:",
                "Rimuovi Libro",
                JOptionPane.QUESTION_MESSAGE,
                null,
                titoliUnici.toArray(),
                titoliUnici.get(0)
        );

        if (selectedTitle == null) return false;

        // 2. Mostra tutti i libri con quel titolo (per disambiguare)
        List<Libro> libriDaRimuovere = libri.stream()
                .filter(l -> l.getTitolo().equals(selectedTitle))
                .toList();

        if (libriDaRimuovere.size() > 1) {
            // Se ci sono più libri con lo stesso titolo, mostra una tabella per la selezione
            String[] columnNames = {"Titolo", "Autore", "ISBN"};
            Object[][] data = libriDaRimuovere.stream()
                    .map(l -> new Object[]{l.getTitolo(), l.getAutore(), l.getIsbn()})
                    .toArray(Object[][]::new);

            JTable table = new JTable(data, columnNames);
            JOptionPane.showMessageDialog(null, new JScrollPane(table),
                    "Seleziona il libro da rimuovere", JOptionPane.PLAIN_MESSAGE);

            int choice = JOptionPane.showConfirmDialog(null,
                    "Confermi la rimozione del libro selezionato?",
                    "Conferma Rimozione",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                libri.removeAll(libriDaRimuovere);
                return true;
            }
            return false;
        } else {
            // Se c'è un solo libro con quel titolo
            int choice = JOptionPane.showConfirmDialog(null,
                    "Confermi la rimozione del libro:\n" +
                            "Titolo: " + libriDaRimuovere.get(0).getTitolo() + "\n" +
                            "Autore: " + libriDaRimuovere.get(0).getAutore(),
                    "Conferma Rimozione",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                libri.remove(libriDaRimuovere.get(0));
                return true;
            }
            return false;
        }
    }
}
