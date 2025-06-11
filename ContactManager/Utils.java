package contactmanager;

import java.util.Scanner;

public class Utils {

    // Cette méthode permet de supprimer les espaces dans un numéro de téléphone.
    public static String normalizePhone(String phone) {
        return phone.replaceAll("\\s+", "");
    }

    // Cette méthode permet de demander l'id d'un Contact.
    // On retourne -1 en cas d'erreur.
    public static int askIdContact(Scanner scanner) {
        System.out.print("Entrez l'ID du contact : ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
            return -1;
        }
    }

    // Cette méthode prend en argument un scanner, un nom de champ, sa valeur actuelle et l'autorisation d'une réponse vide
    // Si la saisie est vide et que le blanc n'est pas autorisé, on retourne null. Sinon, on retourne la saisie.
    public static String askField(Scanner scanner, String label, String currentValue, boolean allowBlank) {
        System.out.print(label + (currentValue != null ? " (" + currentValue + ")" : "") + " : ");
        String input = scanner.nextLine();
        return input.isBlank() && !allowBlank ? null : input;
    }
}
