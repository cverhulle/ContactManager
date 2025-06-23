package contactmanager.features;

import contactmanager.core.Contact;
import contactmanager.core.ContactManager;
import contactmanager.io.ContactIO;
import contactmanager.utils.InputUtils;
import java.util.List;
import java.util.Scanner;

public class ContactImporterExporter {

    // Cette méthode permet de charger automatiquement les contacts au démarrage de l'application
    public static void autoLoadContacts(ContactManager contacts) {

        // On appelle la méthode pour gérer l'import
        Integer added = handleContactImportationFromAPath(contacts, ContactIO.getAutoSavePath());

        // On affiche un message pour l'utilisateur
        System.out.println("Chargement automatique : " + added + " contacts restaurés.");
    }

    // Cette méthode permet de sauvegarder automatiquement les contacts.
    public static void autoSaveContacts(List<Contact> contactsToExport) {
        ContactIO.exportToCSV(contactsToExport, ContactIO.getAutoSavePath());
        System.out.println("Sauvegarde automatique effectuée.");
    }

    // Cette méthode permet de gérer l'export de contact.
    public static void exportContacts(Scanner scanner, List<Contact> contactsToExport) {

        // On demande le chemin où l'on doit exporter le fichier
        String filePath = InputUtils.askInput(scanner, "Chemin du fichier pour exporter (ex: contacts.csv) : ");

        // On lance la méthode d'export.
        ContactIO.exportToCSV(contactsToExport, filePath);

        // Message d'information à la fin de l'écriture du fichier.
        System.out.println("Contacts exportés avec succès !");
    }

    // Cette méthode s'occupe de la gestion de l'import de contact.
    // Elle retourne le nombre de contacts ajoutés.
    private static Integer handleContactImportationFromAPath(ContactManager contacts, String path) {

        // On décrypte le fichier csv dont le chemin est fourni dans path.
        List<Contact> imported = ContactIO.importFromCSV(path);
        
        // On initialise à 0 le nombre de contact ajoutés.
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
        return added;
    }

    // Cette méthode permet de gérer l'import de Contact.
    public static void importContacts(Scanner scanner, ContactManager contacts) {

        // On demande le chemin du fichier à importer.
        String filePath = InputUtils.askInput(scanner, "Chemin du fichier pour exporter (ex: contacts.csv) : ");

        // On appelle la méthode pour gérer l'import
        Integer added = handleContactImportationFromAPath(contacts, filePath);

            // On affiche un message pour l'utilisateur
        System.out.println("Import terminé : " + added + " contacts ajoutés.");
    }
}