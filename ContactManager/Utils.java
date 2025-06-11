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

    // Cette méthode permet d'afficher une question (message) et de retourner la réponse de l'utilisateur.
    public static String askInput(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    // Cette méthode permet de demander à l'utilisateur de confirmer un choix.
    public static boolean confirmChoice(Scanner scanner, String message) {
        System.out.println(message);
        String confirmation = askInput(scanner, "Tapez 'oui' pour confirmer : ");
        return confirmation.equalsIgnoreCase("oui");
    }

    // Cette méthode prend en argument un scanner, un nom de champ, sa valeur actuelle et l'autorisation d'une réponse vide
    // Si la saisie est vide et que le blanc n'est pas autorisé, on répète la question. Sinon, on retourne la saisie.
    public static String askField(Scanner scanner, String label, String currentValue, boolean allowBlank) {
        String input;

        while (true) {
            String prompt = label + (currentValue != null ? " (" + currentValue + ")" : "") + " : ";
            input = askInput(scanner, prompt);

            if (!input.isBlank() || allowBlank) {
                return input;
            }

            System.out.println("Ce champ ne peut pas être vide. Veuillez réessayer.");
        }
    }

    // Cette méthode permet d'afficher un menu (argument options) et elle retourne le choix de l'utilisateur.
    public static String askMenuChoice(Scanner scanner, String title, String[] options) {
        System.out.println("\n" + title);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
        }
        System.out.print("Votre choix : ");
        return scanner.nextLine();
    }
}
