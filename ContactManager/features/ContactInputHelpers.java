package contactmanager.features;

import contactmanager.Contact;
import contactmanager.Tags;
import contactmanager.exception.UserCancelledException;
import contactmanager.utils.InputUtils;
import java.util.Scanner;

public class ContactInputHelpers {

    // Cette méthode permet de centraliser la demande d'un id de contact.
    public static Integer askContactId(Scanner scanner, String action) {
        // On demande l'id de contact.
        try {
            return InputUtils.askIdContact(scanner);

        // Si l'utilisateur choisit d'annuler, on gère l'erreur
        } catch (UserCancelledException e) {
            System.out.println("Action " + action + " annulée.");
            return null;
        }
    }

    // Cette méthode permet de créer un contact à partir des entrées utilisateur.
    public static Contact buildContactFromUserInput(Scanner scanner, Contact existingContact) throws UserCancelledException {
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
