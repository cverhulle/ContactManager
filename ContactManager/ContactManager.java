package contactmanager;

import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    
    // On crée une variable pour stocker la liste des contacts.
    private List<Contact> contacts;

    // Mise en place du constructeur de la classe.
    public ContactManager() {
        this.contacts = new ArrayList<Contact>();
    }
}
