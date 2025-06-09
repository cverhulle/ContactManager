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

    // Méthode pour ajouter un contact.
    // Si l'email ou le numéro de téléphone est déjà utilisé, on n'ajoute pas celui-ci.
    public void addContact(Contact newContact) {
        for (Contact contact : contacts) {
            boolean sameEmail = contact.getEmail().equalsIgnoreCase(newContact.getEmail());
            boolean samePhone = contact.getPhoneNumber().equals(newContact.getPhoneNumber());

            if (sameEmail || samePhone) {
                System.out.println("Contact déjà existant (email ou numéro) : " + contact);
                return;
            }
        }

        contacts.add(newContact);
        System.out.println("Contact ajouté : " + newContact);
    }

    // On gère l'affiche d'un élément de type ContactManager
    @Override
    public String toString() {
        if (contacts.isEmpty()) {
            return "Aucun contact enregistré.";
        }

        StringBuilder listeDeContacts = new StringBuilder();
        listeDeContacts.append("Carnet de contacts :\n");
        for (int i = 0; i < contacts.size(); i++) {
            listeDeContacts.append((i + 1)).append(". ").append(contacts.get(i)).append("\n");
        }
        return listeDeContacts.toString();
    }
}
