package FrontEnd;

import BackEnd.Libro;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class removeDialog {


    public static Libro showRemoveDialog(ArrayList<Libro> libri) {
        if (libri.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nessun libro presente nel sistema!", "Errore", JOptionPane.WARNING_MESSAGE);
            return null;
        }


        List<String> titoliUnici = libri.stream()
                .map(Libro::getTitolo)
                .distinct()
                .collect(Collectors.toList());

        String selectedTitle = (String) JOptionPane.showInputDialog(
                null, "Seleziona il titolo del libro da rimuovere:", "Rimuovi Libro",
                JOptionPane.QUESTION_MESSAGE, null, titoliUnici.toArray(), titoliUnici.get(0));

        if (selectedTitle == null) {
            return null;
        }

        List<Libro> libriCorrispondenti = libri.stream()
                .filter(l -> l.getTitolo().equals(selectedTitle))
                .collect(Collectors.toList());

        Libro libroDaRimuovere;


        if (libriCorrispondenti.size() == 1) {
            libroDaRimuovere = libriCorrispondenti.get(0);
            int choice = JOptionPane.showConfirmDialog(null,
                    "Confermi la rimozione del libro:\n" +
                            "Titolo: " + libroDaRimuovere.getTitolo() + "\n" +
                            "Autore: " + libroDaRimuovere.getAutore(),
                    "Conferma Rimozione", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                return libroDaRimuovere;
            }
        } else {

            String[] columnNames = {"Titolo", "Autore", "ISBN"};
            Object[][] data = libriCorrispondenti.stream()
                    .map(l -> new Object[]{l.getTitolo(), l.getAutore(), l.getIsbn()})
                    .toArray(Object[][]::new);

            JTable table = new JTable(data, columnNames);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            int result = JOptionPane.showConfirmDialog(null, new JScrollPane(table),
                    "Seleziona il libro da rimuovere", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    libroDaRimuovere = libriCorrispondenti.get(selectedRow);
                    return libroDaRimuovere;
                }
            }
        }

        return null;
    }
}
