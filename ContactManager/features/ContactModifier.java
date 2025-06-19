package contactmanager.features;

import contactmanager.core.Contact;
import contactmanager.core.ContactManager;
import contactmanager.exception.UserCancelledException;
import java.util.Scanner;

public class ContactModifier {

    @SuppressWarnings("FieldMayBeFinal")
    private ContactManager contacts;
    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;

    // On crée le constructeur de la classe : 
    // Il initialise une liste de contacts vide et, le scanner.
    public ContactModifier(ContactManager contacts, Scanner scanner) {
        this.contacts = contacts;
        this.scanner = scanner;
    }

    // Cette méthode permet de modifier un Contact.
    public void modifyContact() {

        // On récupère l'id du contact à modifier.
        Integer id = ContactInputHelpers.askContactId(scanner, "de modification");

        if (id == null) return;

        // On récupère les données du contact à modifier
        Contact contact = contacts.getContactById(id);
        if (contact == null) {
            System.out.println("Aucun contact trouvé avec cet ID.");
            return;
        }
        // On lance la méthode pour lancer la modification.
        handleModificationContact(id, contact);
    }

    // Cette méthode permet de gérer la modification de contact.
    private void handleModificationContact(Integer id, Contact contact) {

        try{
            // On entre les données à modifier
            System.out.println("Laissez vide pour ne pas modifier un champ.");

            // On crée un contact mis à jour à partir des données du contact précédent.
            Contact updated = ContactInputHelpers.buildContactFromUserInput(scanner, contact);

            // On met à jour le contact avec les données.
            boolean success = contacts.updateContact(id, updated.getFirstName(), updated.getLastName(), 
                                                        updated.getEmail(), updated.getPhoneNumber(), updated.getTag()); 

            // Si les données sont "valables", on affiche un message de succès. Sinon, c'est un message d'erreur.
            System.out.println(success ? "Contact modifié avec succès !" : "Erreur lors de la modification.");

        } // Si l'utlisateur déclenche l'erreur (en tapant 0), on annule la modification et, on affiche un message.
        catch (UserCancelledException e) {
            System.out.println("Modification annulée");
        }
    }

}
