package BackEnd;

import java.util.Scanner;

public class inputHandler {
    private static Scanner scanner = new Scanner(System.in);

    // Metodo per leggere stringhe (con controllo non vuoto)
    public String leggiStringa(String messaggio) {
        String input;
        boolean valido = false;
        do {
            System.out.print(messaggio);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Errore: Il campo non può essere vuoto!");
            }
            if ( input.matches(".*\\d.*")) {
                System.out.println("Errore: Il campo non può essere un numero!");
            }
            else
                valido = true;
        } while (!valido);
        return input;
    }

    // Metodo per leggere interi
    public int leggiIntero(String messaggio) {
        while (true) {
            try {
                System.out.print(messaggio);
                int valore = Integer.parseInt(scanner.nextLine());
                return valore;
            } catch (NumberFormatException e) {
                System.out.println("Errore: Devi inserire un numero intero valido!");
            }
        }
    }

    // Metodo per leggere interi con range
    public int leggiInteroRange(String messaggio, int min, int max) {
        while (true) {
            int valore = leggiIntero(messaggio);
            if (valore >= min && valore <= max) {
                return valore;
            }
            System.out.println("Errore: Il valore deve essere tra " + min + " e " + max + "!");
        }
    }


    // Chiudi lo scanner quando non serve più
    private static void closeScanner() {
        scanner.close();
    }
}