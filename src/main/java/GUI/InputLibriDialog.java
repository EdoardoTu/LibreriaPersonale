package GUI;
import Template.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InputLibriDialog {
    public static ArrayList<Libro> showBookInputDialog() {
        ArrayList<Libro> libri = new ArrayList<>();
        boolean continua = true;

        while (continua) {
            // Pannello per gli input
            JPanel panel = new JPanel(new GridLayout(0, 2));

            // Campi di input
            JTextField titoloField = new JTextField();
            JTextField autoreField = new JTextField();
            JTextField isbnField = new JTextField();
            JComboBox<Libro.Genere> genereCombo = new JComboBox<>(Libro.Genere.values());
            JComboBox<Libro.Stato> statoCombo = new JComboBox<>(Libro.Stato.values());
            JSpinner valutazioneSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));

            // Aggiungi componenti al pannello
            panel.add(new JLabel("Titolo*:"));
            panel.add(titoloField);
            panel.add(new JLabel("Autore*:"));
            panel.add(autoreField);
            panel.add(new JLabel("ISBN*:"));
            panel.add(isbnField);
            panel.add(new JLabel("Genere:"));
            panel.add(genereCombo);
            panel.add(new JLabel("Stato:"));
            panel.add(statoCombo);
            panel.add(new JLabel("Valutazione (1-5):"));
            panel.add(valutazioneSpinner);

            // Mostra il dialog e verifica l'OK
            int result = JOptionPane.showConfirmDialog(
                    null, panel, "Aggiungi Libro",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Validazione Titolo (non vuoto e senza numeri)
                    String titolo = titoloField.getText().trim();
                    if (titolo.isEmpty() || titolo.matches(".*\\d.*")) {
                        throw new IllegalArgumentException("Titolo non valido: no numeri/campi vuoti");
                    }

                    // Validazione Autore (non vuoto e senza numeri)
                    String autore = autoreField.getText().trim();
                    if (autore.isEmpty() || autore.matches(".*\\d.*")) {
                        throw new IllegalArgumentException("Autore non valido: no numeri/campi vuoti");
                    }

                    // Validazione ISBN (numero intero)
                    int isbn;
                    try {
                        isbn = Integer.parseInt(isbnField.getText().trim());
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("ISBN deve essere un numero intero");
                    }

                    // Ottenimento altri valori
                    Libro.Genere genere = (Libro.Genere) genereCombo.getSelectedItem();
                    Libro.Stato stato = (Libro.Stato) statoCombo.getSelectedItem();
                    if(stato == Libro.Stato.da_leggere || stato == Libro.Stato.in_lettura){
                        valutazioneSpinner.hide();
                        libri.add(new Libro(titolo, autore, isbn, genere, -1, stato));

                        // Chiedi se continuare
                        continua = JOptionPane.showConfirmDialog(
                                null, "Aggiungere un altro libro?", "Continua?",
                                JOptionPane.YES_NO_OPTION
                        ) == JOptionPane.YES_OPTION;
                    }
                    else {
                        int valutazione = (int) valutazioneSpinner.getValue();

                        // Aggiungi il libro alla lista
                        libri.add(new Libro(titolo, autore, isbn, genere, valutazione, stato));

                        // Chiedi se continuare
                        continua = JOptionPane.showConfirmDialog(
                                null, "Aggiungere un altro libro?", "Continua?",
                                JOptionPane.YES_NO_OPTION
                        ) == JOptionPane.YES_OPTION;
                    }
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(
                            null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                continua = false;
            }
        }
        return libri;
    }

}