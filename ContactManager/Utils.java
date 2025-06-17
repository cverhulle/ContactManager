package contactmanager;

import java.util.Scanner;
import java.util.function.Consumer;

/* A FAIRE : 
 * Gérer la recherche avec les tags.
*/

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
            String prompt = promptLabel(label, currentValue);
            input = askInput(scanner, prompt);

            // Si la réponse est 0, on considère que l'utilisater annule.
            if (isCancelChoice(input)) {
                throw new CancelledInputException();
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
    // On retourne une erreur en cas d'annulation "0" ou de mauvaise entrée.
    public static int askIdContact(Scanner scanner) {
        String answer = askInput(scanner, "Entrez l'ID du contact (0 pour annuler) :");

        // Si l'entrée est "0", on retourne une erreur
        if (isCancelChoice(answer)) {
            throw new CancelledInputException();
        }

        // On transforme l'entrée en Integer. En cas d'échec, on retourne une erreur.
        try {
            return Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    // Cette méthode permet d'afficher une question (message) et de retourner la réponse de l'utilisateur.
    public static String askInput(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    // Cette méthode permet d'afficher un menu (argument options)
    // L'argument cancelLabel permet d'afficher un message si l'on veut revenir au menu principal (null si non souhaité)
    // Cette méthode retourne : Une erreur si on choisit de revenir au menu principal ou la réponse de l'utilisateur
    public static String askMenuChoice(Scanner scanner, String title, String[] options, String cancelLabel, boolean allowBlank) {
        String answer;

        // On crée une boucle infinie
        while(true) {

            // On affiche le menu de choix
            displayMenuChoice(title, options, cancelLabel);

            // On récupère le choix de l'utilisateur
            answer = askInput(scanner, "Votre choix : ");

            // Si l'annulation est choisie et possible, on retourne l'erreur
            if (cancelLabel != null && isCancelChoice(answer)) {
                throw new CancelledInputException();
            }

            // Si la réponse est vide et que le vide est autorisé, on retourne la réponse.
            if (answer.isBlank() && allowBlank) {
                return answer;
            }

            // Si la réponse n'est pas recevable (string, nombre trop grand etc...), on repart en début de boucle
            if (!isValidMenuChoice(answer, options.length)) {
                System.out.print("L'entrée saisie n'est pas valide");
                continue;
            }

            // Si tout est ok, on retourne la réponse.
            return answer;   
        }
    }

    // Cette méthode permet de demander à l'utilisateur de choisir parmi la liste des Tags.
    // Elle retourne sa réponse ou une erreur si l'utilisateur choisit d'annuler.
    public static Tags askTag(Scanner scanner,  String label, Tags currentValue, boolean allowBlank) {

        // On récupère la liste des Tags en type Tags (pour le retour) et en string (pour l'analyse)
        Tags[] tags = Tags.values();
        String[] tagNames = Tags.getTagsNamesInString();

        // On gère la question à afficher
        String prompt = promptLabel(label, (currentValue == null) ? "" : currentValue.toString());

        // Demander à l'utilisateur de faire son choix
        String choice = askMenuChoice(scanner, prompt, tagNames, "Annuler",allowBlank);

        // Si choice est une chaîne vide et, que le vide est autorisé, on retourne la valeure précédente
        if (allowBlank && choice.trim().isEmpty()) {
            return currentValue;
        } else {

            // Sinon, on retourne la nouvelle valeur.
            int index = Integer.parseInt(choice) - 1;
            return tags[index];        
        }
    }

    // Cette méthode permet de demander à l'utilisateur de confirmer un choix en affichant un message d'information au-dessus.
    public static boolean confirmChoice(Scanner scanner, String message) {
        System.out.println(message);
        String confirmation = askInput(scanner, "Tapez 'oui' pour confirmer : ");
        return confirmation.equalsIgnoreCase("oui");
    }

    // Cette méthode permet d'afficher un menu de choix
    public static void displayMenuChoice(String title, String[] options, String cancelLabel) {

        // On affiche la question posée et, les options.
        System.out.println("\n" + title);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
        }

        // Si l'utilisateur peut annuler, on afficher le message
        if(cancelLabel != null) {
            System.out.println("0. " + cancelLabel);
        }
    }

    // Cette méthode permet de tester si l'utilisateur souhaite annuler un choix.
    public static boolean isCancelChoice(String choice) {
        return "0".equals(choice);
    }

    // Cette méthode retourne true si la variable est null ou vide.
    public static boolean isNotNullOrBlank(String value) {
        return value != null && !value.isBlank();
    }

    // Cette méthode permet de voir si le choix fait dans un menu est recevable.
    public static boolean isValidMenuChoice(String choice, int numberOfOptions) {

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

    // Cette méthode permet de citer l'attribut de contact et, entre parenthèses, citer la valeur actuelle
    public static String promptLabel(String label, String currentValue) {
        return label + (currentValue != null ? "(" + currentValue + ")" : "") + " (0 pour annuler) : ";
    }
}
