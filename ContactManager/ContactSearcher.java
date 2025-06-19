package contactmanager;

import contactmanager.utils.InputUtils;
import contactmanager.utils.SearchUtils;
import java.util.List;
import java.util.Scanner;

public class ContactSearcher {

    @SuppressWarnings("FieldMayBeFinal")
    private ContactManager contacts;
    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;

    // On crée le constructeur de la classe : 
    // Il initialise une liste de contacts vide et, le scanner.
    public ContactSearcher(ContactManager contacts, Scanner scanner) {
        this.contacts = contacts;
        this.scanner = scanner;
    }

    // Cette méthode permet de rechercher un contact lorsqu'on est dans le cas Tag.
    // Elle retourne la liste de contacts correspondant au critère.
    private List<Contact> findContactByTag() {

        // On affiche toutes les options de tag
        Tags tagChoice = InputUtils.askTag(scanner, "Choisissez un tag :", null, true);

        // Si l'entrée est vide, on affiche tous les contacts.
        if (tagChoice == null) {
           return contacts.getAllContacts();
        } else {
            // Sinon, on affiche les résultats.
            return SearchUtils.searchByTag(contacts.getAllContacts(), tagChoice);
        }
    }

    // Cette méthode permet de gérer la recherche de contact
    public List<Contact> findContact() {

        // On affiche la liste des champs de recherche possible.
        String[] options = { "Prénom", "Nom", "Email", "Téléphone", "Tag" };

        try{ 
            String fieldChoice = InputUtils.askMenuChoice(scanner, "Rechercher par :", options, "Annuler la recherche",false);

            // Si on choisit de chercher par Tags.
            if ("5".equals(fieldChoice)) { 
                return findContactByTag();
            
            // Si l'option Tag n'est pas chosie
            } else {

                // On demande la donnée à rechercher
                String query = InputUtils.askInput(scanner, "Entrez la valeur à rechercher : ");

                // On lance la recherche correspondante
                return switch (fieldChoice) {
                    case "1" -> SearchUtils.searchByFirstName(contacts.getAllContacts(), query);
                    case "2" -> SearchUtils.searchByLastName(contacts.getAllContacts(), query);
                    case "3" -> SearchUtils.searchByEmail(contacts.getAllContacts(), query);
                    case "4" -> SearchUtils.searchByPhone(contacts.getAllContacts(), query);
                    default -> throw new IllegalStateException("Unexpected value: " + fieldChoice);
                };
            }  
        } // Si l'utlisateur déclenche l'erreur (en tapant 0), on annule la recherche et, on affiche un message.
        catch (UserCancelledException e) {
            System.out.println("Recherche annulée");
            return null;
        }
    }

}
