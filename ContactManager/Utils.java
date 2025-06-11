package contactmanager;

import java.util.Scanner;

public class Utils {

    // Cette méthode permet de supprimer les espaces dans un numéro de téléphone.
    public static String normalizePhone(String phone) {
        return phone.replaceAll("\\s+", "");
    }

    // Cette méthode permet de demander l'id d'un Contact.
    // On retourne -1 en cas d'erreur.
    public static int demanderIdContact(Scanner scanner) {
        System.out.print("Entrez l'ID du contact : ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
            return -1;
        }
    }
}
