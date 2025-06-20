package contactmanager.features;

import contactmanager.core.Contact;
import contactmanager.io.ContactIO;
import java.util.List;

public class ContactImporterExporter {

    // Cette méthode permet de gérer l'export de contact.
    public static void exportContacts(List<Contact> contacts) {
        ContactIO.exportToCSV(contacts);
    }
}