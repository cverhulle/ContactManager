package contactmanager;

import java.util.Scanner;

public class ContactRemover {

    @SuppressWarnings("FieldMayBeFinal")
    private ContactManager contacts;
    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;

    // On crée le constructeur de la classe : 
    // Il initialise une liste de contacts vide et, le scanner.
    public ContactRemover(ContactManager contacts, Scanner scanner) {
        this.contacts = contacts;
        this.scanner = scanner;
    }

}
