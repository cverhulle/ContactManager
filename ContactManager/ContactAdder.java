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
            Contact newContact = buildContactFromUserInput(null);
            contacts.addContact(newContact);
        
        // Si l'utilisateur tape 0, on récupère l'erreur et on annule l'ajout.
        } catch (UserCancelledException e) {
            System.out.println("Ajout annulé.");
        }
    }

    // Cette méthode permet de créer un contact à partir des entrées utilisateur.
    private Contact buildContactFromUserInput(Contact existingContact) throws UserCancelledException {
        String firstName = InputUtils.askField(scanner, "Prénom", 
                                        existingContact == null ? null : existingContact.getFirstName(), 
                                        existingContact != null);
        String lastName = InputUtils.askField(scanner, "Nom", 
                                        existingContact == null ? null : existingContact.getLastName(), 
                                        existingContact != null);
        String email = InputUtils.askField(scanner, "Email", 
                                        existingContact == null ? null : existingContact.getEmail(), 
                                        existingContact != null);
        String phone = InputUtils.askField(scanner, "Téléphone", 
                                        existingContact == null ? null : existingContact.getPhoneNumber(), 
                                        existingContact != null);
        Tags tag = InputUtils.askTag(scanner, "Tag", 
                                        existingContact == null ? null : existingContact.getTag(), 
                                        existingContact != null);

        return new Contact(lastName, firstName, email, phone, tag);
    }
}
