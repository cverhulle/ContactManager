package contactmanager.utils;

import java.util.function.Consumer;

// Cette classe regroupe les "diverses" méthodes
public abstract class Utils {

    // Cette méthode permet d'appliquer un setter seulement si la valeur n'est pas null ou vide.
    public static void applyIfNotBlank(String value, Consumer<String> setter) {
        if (isNotNullOrBlank(value)) {
            setter.accept(value);
        }
    }

    // Cette méthode permet de citer l'attribut de contact et, entre parenthèses, citer la valeur actuelle
    public static String buildPromptLabel(String label, String currentValue) {
        return label + (currentValue != null ? "(" + currentValue + ")" : "") + " (0 pour annuler) : ";
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

    // Cette méthode transforme la réponse au choix du mode de tri (croissant ou décroissant) de string en booléen
    public static Boolean transformDirectionStringInBoolean(String choice) {
        return choice.equals("1");
    }
}