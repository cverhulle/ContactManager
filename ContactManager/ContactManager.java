package contactmanager;

import java.util.ArrayList;
import java.util.List;

// Cette classe permet de gérer le carnet d'adresse.
public class ContactManager {
    
    // On crée une variable pour stocker la liste des contacts.
    final private List<Contact> contacts;

    // Mise en place du constructeur de la classe.
    public ContactManager() {
        this.contacts = new ArrayList<>();
    }

    // Ajout d'une méthode pour ajouter un contact.
    public void addContact(Contact contact) {
        if (contacts.contains(contact)) {
            System.out.println("⚠️ Ce contact existe déjà (même email).");
        } else {
            contacts.add(contact);
            System.out.println("✅ Contact ajouté : " + contact.getFirstName() + " " + contact.getLastName());
        }
    }
}
