package contactmanager.features;

import contactmanager.core.Contact;
import contactmanager.core.ContactManager;
import contactmanager.io.ContactIO;
import java.util.List;

public class ContactImporterExporter {

    // Cette méthode permet de gérer l'export de contact.
    public static void exportContacts(List<Contact> contactsToExport) {
        ContactIO.exportToCSV(contactsToExport);
    }

    // Cette méthode permet de gérer l'import de Contact.
    public static void importContacts(ContactManager contacts) {

        // On récupère la liste des contacts avec la méthode d'import.
        List<Contact> imported = ContactIO.importFromCSV();
        int added = 0;

        // On crée une boucle pour ne pas ajouter les contacts déjà dans notre liste.
        for (Contact contact : imported) {
            if (!contacts.getAllContacts().stream().anyMatch(
                    c -> c.getEmail().equalsIgnoreCase(contact.getEmail())
                        || c.getPhoneNumber().equals(contact.getPhoneNumber()))) {
                contacts.addContact(contact);
                added++;
            }
        }
        System.out.println("Import terminé : " + added + " contacts ajoutés.");
    }
}