package contactmanager.features;

import contactmanager.core.Contact;
import contactmanager.exception.UserCancelledException;
import contactmanager.utils.InputUtils;
import contactmanager.utils.SortUtils;
import java.util.List;
import java.util.Scanner;

// Cette feature s'occupe du tri des contacts.
public class ContactSorter {

    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;

    // On crée le constructeur de la classe : 
    // Il initialise le scanner.
    public ContactSorter(Scanner scanner) {
        this.scanner = scanner;
    }

    // Cette méthode permet de gérer le tri d'une liste de contact.
    public List<Contact> sortContact(List<Contact> inputList) {
        // On initialise les variables pour le tri
        String[] options = { "Prénom", "Nom", "Email", "Téléphone", "Tag"};

        // On demande à l'utilisateur s'il veut trier ou revenir en arrière
        System.out.println("\nVoulez-vous trier les résultats ?");

        try{
            // On demande sur quel attribut s'effectuera le tri
            String choice = InputUtils.askMenuChoice(scanner, "Choisissez le filtre pour trier les données", options, "Quitter", false);

            // On demande si le tri est croissant ou décroissant
            boolean ascending = InputUtils.askSortDirectionInBoolean(scanner);

            // En fonction des choix réalisés, on appelle la méthode correspondante.
            return getSortedContactsByChoice(inputList, choice, ascending);
        }  
        // Si l'utlisateur déclenche l'erreur (en tapant 0), on annule la recherche et, on affiche un message.
        catch (UserCancelledException e) {
            System.out.println("Retour au menu principal.");
            return inputList;
        }
    }

    // Cette méthode retourne la liste de contacts entières triés selon le choix et dans l'ordre donné par ascending.
    private List<Contact> getSortedContactsByChoice(List<Contact> inputList, String choice, boolean ascending) {
        return switch (choice) {
            case "1" -> SortUtils.getContactsSortedByFirstName(inputList, ascending);
            case "2" -> SortUtils.getContactsSortedByLastName(inputList, ascending);
            case "3" -> SortUtils.getContactsSortedByEmail(inputList, ascending);
            case "4" -> SortUtils.getContactsSortedByPhoneNumber(inputList, ascending);
            case "5" -> SortUtils.getContactsSortedByTag(inputList, ascending);
            default -> inputList;
        };
    }

}