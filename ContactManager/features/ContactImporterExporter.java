package contactmanager.features;

import contactmanager.core.Contact;
import contactmanager.core.ContactManager;
import contactmanager.io.ContactIO;
import contactmanager.utils.InputUtils;
import java.util.List;
import java.util.Scanner;

public class ContactImporterExporter {

    // Cette méthode permet de gérer l'export de contact.
    public static void exportContacts(Scanner scanner, List<Contact> contactsToExport) {

        // On demande le chemin où l'on doit exporter le fichier
        String filePath = InputUtils.askInput(scanner, "Chemin du fichier pour exporter (ex: contacts.csv) : ");

        // On lance la méthode d'export.
        ContactIO.exportToCSV(contactsToExport, filePath);
    }

    // Cette méthode permet de gérer l'import de Contact.
    public static void importContacts(Scanner scanner, ContactManager contacts) {

        // On demande le chemin du fichier à importer.
        String filePath = InputUtils.askInput(scanner, "Chemin du fichier pour exporter (ex: contacts.csv) : ");

        // On récupère la liste des contacts avec la méthode d'import.
        List<Contact> imported = ContactIO.importFromCSV(filePath);
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