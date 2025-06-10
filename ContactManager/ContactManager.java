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

        // On récupère le numéro de téléphone de la personne à ajouter en supprimant les espaces.
        String newPhone = Utils.normalizePhone(newContact.getPhoneNumber());

        for (Contact contact : contacts) {

            // On regarde si l'email est déjà utilisé
            boolean sameEmail = contact.getEmail().equalsIgnoreCase(newContact.getEmail());

            // On récupère le numéro de téléphone dans la liste des contacts en supprimant les espaces.
            String existingPhone = Utils.normalizePhone(contact.getPhoneNumber());

            // On regarde si le numéro de téléphone est déjà utilisé.
            boolean samePhone = newPhone.equals(existingPhone);

            // Si l'email ou le téléphone est déjà pris...
            if (sameEmail || samePhone) {

                // On affiche un message et, on n'ajoute pas le contact
                System.out.println("Contact déjà existant : " + contact);
                return;
            }
        }

        // Sinon, on ajoute le contact.
        contacts.add(newContact);
        System.out.println("Contact ajouté : " + newContact);
    }

    // Cette méthode permet de supprimer un contact par son id.
    public void removeContactById(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                contacts.remove(contact);
                System.out.println("Contact supprimé : " + contact);
                return;
            }
        }
        System.out.println("Aucun contact trouvé avec l'id : " + id);
    }

    // Cette méthode sera appelée dans les différentes voies pour trouver un contact.
    private void searchGeneric(String label, java.util.function.Predicate<Contact> predicate) {
        boolean found = false;
        for (Contact contact : contacts) {
            if (predicate.test(contact)) {
                System.out.println("Résultat trouvé : " + contact);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Aucun contact ne correspond au champ de recherche : " + label);
        }
    }

    // Cette méthode permet de trouver un contact en donnant un prénom.
    public void searchByFirstName(String query) {
        searchGeneric("prénom", contact -> contact.getFirstName().toLowerCase().contains(query.toLowerCase()));
    }

    // Cette méthode permet de trouver un contact en donnant un nom.
    public void searchByLastName(String query) {
        searchGeneric("nom", contact -> contact.getLastName().toLowerCase().contains(query.toLowerCase()));
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
