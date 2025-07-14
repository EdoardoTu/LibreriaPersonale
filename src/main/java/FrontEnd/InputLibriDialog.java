package FrontEnd;


import BackEnd.Libro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InputLibriDialog {
    public static ArrayList<Libro> showBookInputDialog() {
        ArrayList<Libro> libri = new ArrayList<>();
        boolean continua = true;

        while (continua) {
            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5)); // Aggiunto un po' di spazio

            // Campi di input
            JTextField titoloField = new JTextField();
            JTextField autoreField = new JTextField();
            JTextField isbnField = new JTextField();
            JComboBox<Libro.Genere> genereCombo = new JComboBox<>(Libro.Genere.values());
            JComboBox<Libro.Stato> statoCombo = new JComboBox<>(Libro.Stato.values());
            JSpinner valutazioneSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));

            // Aggiunta dell'ActionListener per abilitare/disabilitare la valutazione
            statoCombo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Libro.Stato selectedStato = (Libro.Stato) statoCombo.getSelectedItem();
                    if (selectedStato == Libro.Stato.da_leggere || selectedStato == Libro.Stato.in_lettura) {
                        valutazioneSpinner.setEnabled(false);
                    } else {
                        valutazioneSpinner.setEnabled(true);
                    }
                }
            });

            // Imposta lo stato iniziale del JSpinner
            Libro.Stato selectedStato = (Libro.Stato) statoCombo.getSelectedItem();
            if (selectedStato == Libro.Stato.da_leggere || selectedStato == Libro.Stato.in_lettura) {
                valutazioneSpinner.setEnabled(false);
            }

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

            int result = JOptionPane.showConfirmDialog(
                    null, panel, "Aggiungi Libro",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String titolo = titoloField.getText().trim();
                    if (titolo.isEmpty() || titolo.matches(".*\\d.*")) {
                        throw new IllegalArgumentException("Titolo non valido: no numeri/campi vuoti");
                    }

                    String autore = autoreField.getText().trim();
                    if (autore.isEmpty() || autore.matches(".*\\d.*")) {
                        throw new IllegalArgumentException("Autore non valido: no numeri/campi vuoti");
                    }

                    long isbn;
                    try {
                        isbn = Long.parseLong(isbnField.getText().trim());
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("ISBN deve essere un numero valido");
                    }

                    Libro.Genere genere = (Libro.Genere) genereCombo.getSelectedItem();
                    Libro.Stato stato = (Libro.Stato) statoCombo.getSelectedItem();
                    int valutazione = -1; // Valore di default

                    if (valutazioneSpinner.isEnabled()) {
                        valutazione = (int) valutazioneSpinner.getValue();
                    }

                    libri.add(new Libro(titolo, autore, isbn, genere, valutazione, stato));

                    continua = JOptionPane.showConfirmDialog(
                            null, "Aggiungere un altro libro?", "Continua?",
                            JOptionPane.YES_NO_OPTION
                    ) == JOptionPane.YES_OPTION;

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