package contactmanager;

import java.util.Scanner;


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
    private void addContact() {

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
