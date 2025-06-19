package contactmanager;

import java.util.Scanner;

public class InputUtils {


    // Cette méthode prend en argument un scanner, un nom de champ, sa valeur actuelle et l'autorisation d'une réponse vide
    // Si la saisie est vide et que le blanc n'est pas autorisé, on répète la question. Sinon, on retourne la saisie.
    public static String askField(Scanner scanner, String label, String currentValue, boolean allowBlank) {
        String input;

        // On crée une boucle infinie 
        while (true) {

            // On demande à l'utilisateur de répondre à une question
            String prompt = Utils.buildPromptLabel(label, currentValue);
            input = askInput(scanner, prompt).trim();

            // Si la réponse est 0, on considère que l'utilisater annule.
            if (Utils.isCancelChoice(input)) {
                throw new UserCancelledException();
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
        if (Utils.isCancelChoice(answer)) {
            throw new UserCancelledException();
        }

        // On transforme l'entrée en Integer. En cas d'échec, on relance la méthode.
        try {
            return Integer.parseInt(answer.trim());
        } catch (NumberFormatException e) {
            System.out.println("L'ID n'est pas un entier.");
            return askIdContact(scanner);
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
            Utils.displayMenuChoice(title, options, cancelLabel);

            // On récupère le choix de l'utilisateur
            answer = askInput(scanner, "Votre choix : ").trim();

            // Si l'annulation est choisie et possible, on retourne l'erreur
            if (cancelLabel != null && Utils.isCancelChoice(answer)) {
                throw new UserCancelledException();
            }

            // Si la réponse est vide et que le vide est autorisé, on retourne la réponse.
            if (answer.isBlank() && allowBlank) {
                return answer;
            }

            // Si la réponse n'est pas recevable (string, nombre trop grand etc...), on repart en début de boucle
            if (!Utils.isValidMenuChoice(answer, options.length)) {
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
        String prompt = Utils.buildPromptLabel(label, (currentValue == null) ? "" : currentValue.toString());

        // Demander à l'utilisateur de faire son choix
        String choice = askMenuChoice(scanner, prompt, tagNames, "Annuler",allowBlank).trim();

        // Si choice est une chaîne vide et, que le vide est autorisé, on retourne la valeure précédente
        if (allowBlank && choice.trim().isEmpty()) {
            return currentValue;
        } else {

            // Sinon, on retourne la nouvelle valeur.
            int index = Integer.parseInt(choice) - 1;
            return tags[index];        
        }
    }

    // Cette méthode retourne true si le choix de tri est croissant et, false si c'est décroissant
    public static Boolean chooseDirectionInBoolean(Scanner scanner) {
        String directionChoice = chooseDirectionInString(scanner);
        return Utils.transformDirectionStringInBoolean(directionChoice);
    }

    // Cette méthode permet de choisir la manière de trier (croissant ou décroissant) en retournant "1" pour croissant et "2" pour décroissant.
    private static String chooseDirectionInString(Scanner scanner) {
        String[] direction = {"Croissant", "Décroissant"};
        return askMenuChoice(scanner, "Quel est le sens de tri ?", direction, "Quitter", false);
    }

    // Cette méthode permet de demander à l'utilisateur de confirmer un choix en affichant un message d'information au-dessus.
    public static boolean confirmChoice(Scanner scanner, String message) {
        System.out.println(message);
        String confirmation = askInput(scanner, "Tapez 'oui' pour confirmer : ").trim();
        return confirmation.equalsIgnoreCase("oui");
    }
}
