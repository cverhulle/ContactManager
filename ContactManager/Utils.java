package contactmanager;

import java.util.Scanner;
import java.util.function.Consumer;

public class Utils {

    // Cette méthode permet d'appliquer un setter seulement si la valeur n'est pas null ou vide.
    public static void applyIfNotBlank(String value, Consumer<String> setter) {
        if (isNotNullOrBlank(value)) {
            setter.accept(value);
        }
    }

    // Cette méthode prend en argument un scanner, un nom de champ, sa valeur actuelle et l'autorisation d'une réponse vide
    // Si la saisie est vide et que le blanc n'est pas autorisé, on répète la question. Sinon, on retourne la saisie.
    public static String askField(Scanner scanner, String label, String currentValue, boolean allowBlank) {
        String input;

        // On crée une boucle infinie 
        while (true) {

            // On demande à l'utilisateur de répondre à une question
            String prompt = label + (currentValue != null ? "(" + currentValue + ")" : "") + "(0 pour annuler) : ";
            input = askInput(scanner, prompt);

            // Si la réponse est 0, on considère que l'utilisater annule.
            if (input.equals("0")) {
                return null;
            }

            // Si la réponse n'est pas vide ou que le vide est autorisé, on retourne la réponse.
            if (!input.isBlank() || allowBlank) {
                return input;
            }

            // Si la réponse est vide et que ce n'est pas autorisé, on affiche une erreur et on recommence la boucle.
            System.out.println("Ce champ ne peut pas être vide. Veuillez réessayer.");
        }
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

    // Cette méthode permet d'afficher un menu (argument options)
    // L'argument cancelLabel permet d'afficher un message si l'on veut retourner en arrière
    // Cette méthode retourne le choix de l'utilisateur ou null si on choisit de revenir au menu principal.
    public static String askMenuChoice(Scanner scanner, String title, String[] options, String cancelLabel) {
        System.out.println("\n" + title);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
        }

        if(cancelLabel != null) {
            System.out.println("0. " + cancelLabel);
        }
        System.out.print("Votre choix : ");
        String answer = scanner.nextLine();

        if (cancelLabel != null && answer.equals("0")) {
            return null; 
        }

        return answer;
    }

    // Cette méthode permet de demander à l'utilisateur de confirmer un choix.
    public static boolean confirmChoice(Scanner scanner, String message) {
        System.out.println(message);
        String confirmation = askInput(scanner, "Tapez 'oui' pour confirmer : ");
        return confirmation.equalsIgnoreCase("oui");
    }

    // Cette méthode permet de tester si l'utilisateur souhaite annuler un choix.
    public static boolean isCancelChoice(String choice) {
        return "0".equals(choice);
    }

    // Cette méthode retourne true si la variable est null ou vide.
    public static boolean isNotNullOrBlank(String value) {
        return value != null && !value.isBlank();
    }

    // Cette méthode permet de voir si le choix fait dans un menu est recevable
    public static boolean isValidMenuChoice(String choice, int numberOfOptions) {

        // Dans le cas où le choix est 0, on retourne false
        if (choice == null) return false;

        // On regarde si le choix est entre 1 et le nombre d'options du menu.
        try {
            int value = Integer.parseInt(choice);
            return value >= 1 && value <= numberOfOptions;
        
        // En cas d'erreur (mauvaise entrée utilisateur par exemple), on retourne false
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Cette méthode permet de supprimer les espaces dans un numéro de téléphone.
    public static String normalizePhone(String phone) {
        return phone.replaceAll("\\s+", "");
    }
}
