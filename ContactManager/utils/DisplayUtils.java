package contactmanager.utils;

// Cette classe s'occupe des méthodes d'affichage dans le programme.
public class DisplayUtils {

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
}
