package contactmanager.features;

import contactmanager.core.Contact;
import contactmanager.core.ContactManager;
import contactmanager.exception.UserCancelledException;
import java.util.Scanner;

// Cette feature gère l'ajout d'un contact dans une liste de contact.
public class ContactAdder {

    @SuppressWarnings("FieldMayBeFinal")
    private ContactManager contacts;
    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;

    // On crée le constructeur de la classe : 
    // Il initialise une liste de contacts vide et, le scanner.
    public ContactAdder(ContactManager contacts, Scanner scanner) {
        this.contacts = contacts;
        this.scanner = scanner;
    }

    // Cette métohde permet d'ajouter un contact dans la liste.
    public void addContact() {

        // On demande les champs du contact à l'utilisateur
        try{ 
            Contact newContact = ContactInputHelpers.buildContactFromUserInput(scanner, null);
            contacts.addContact(newContact);
        
        // Si l'utilisateur tape 0, on récupère l'erreur et on annule l'ajout.
        } catch (UserCancelledException e) {
            System.out.println("Ajout annulé.");
        }
    }
}
