package FrontEnd;

import Template.Libro;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class removeDialog {

    // Il metodo ora restituisce il Libro selezionato o null se l'operazione è annullata.
    public static Libro showRemoveDialog(ArrayList<Libro> libri) {
        if (libri.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nessun libro presente nel sistema!", "Errore", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // 1. Selezione del titolo (invariato)
        List<String> titoliUnici = libri.stream()
                .map(Libro::getTitolo)
                .distinct()
                .collect(Collectors.toList());

        String selectedTitle = (String) JOptionPane.showInputDialog(
                null, "Seleziona il titolo del libro da rimuovere:", "Rimuovi Libro",
                JOptionPane.QUESTION_MESSAGE, null, titoliUnici.toArray(), titoliUnici.get(0));

        if (selectedTitle == null) {
            return null; // L'utente ha annullato
        }

        // 2. Filtra i libri con il titolo selezionato
        List<Libro> libriCorrispondenti = libri.stream()
                .filter(l -> l.getTitolo().equals(selectedTitle))
                .collect(Collectors.toList());

        Libro libroDaRimuovere;

        // 3. Se c'è un solo libro con quel titolo, chiedi conferma e restituiscilo
        if (libriCorrispondenti.size() == 1) {
            libroDaRimuovere = libriCorrispondenti.get(0);
            int choice = JOptionPane.showConfirmDialog(null,
                    "Confermi la rimozione del libro:\n" +
                            "Titolo: " + libroDaRimuovere.getTitolo() + "\n" +
                            "Autore: " + libroDaRimuovere.getAutore(),
                    "Conferma Rimozione", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                return libroDaRimuovere; // Restituisce il libro da rimuovere
            }
        } else {
            // 4. Se ci sono più libri, falli scegliere da una tabella
            String[] columnNames = {"Titolo", "Autore", "ISBN"};
            Object[][] data = libriCorrispondenti.stream()
                    .map(l -> new Object[]{l.getTitolo(), l.getAutore(), l.getIsbn()})
                    .toArray(Object[][]::new);

            JTable table = new JTable(data, columnNames);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permetti solo una selezione

            int result = JOptionPane.showConfirmDialog(null, new JScrollPane(table),
                    "Seleziona il libro da rimuovere", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    libroDaRimuovere = libriCorrispondenti.get(selectedRow); // Prendi il libro selezionato
                    return libroDaRimuovere; // Restituisci il libro
                }
            }
        }

        return null; // L'utente ha annullato o non ha selezionato nulla
    }
}
