package contactmanager.core;

import contactmanager.utils.Utils;
import java.util.ArrayList;
import java.util.List;

// Cette classe permet de construire et de gérer le carnet d'adresse (liste de contacts).
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

        // Si l'email ou le téléphone est déjà pris...
        if (isPhoneOrEmailUsed(newContact.getPhoneNumber(), newContact.getEmail(), -1)) {
            // On affiche un message et, on n'ajoute pas le contact
            System.out.println("Un contact avec cet email ou téléphone existe déjà !");
            return ;
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

        // Cette métohde permet de récupérer un Contact à partir de son id en argument.
    public Contact getContactById(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }   

    // Cette méthode permet de vérifier si l'email est déjà utilisé par un contact (en excluant un id donné)
    private boolean isEmailUsed(String email, int excludeId) {
        for (Contact contact : contacts) {
            if (contact.getId() != excludeId && email.equalsIgnoreCase(contact.getEmail())) {
                return true;
            }
        }
        return false;
    }

    // Cette méthode permet de vérifier si le numéro de téléphone est déjà utilisé par un contact (en excluant un id donné)
    private boolean isPhoneUsed(String phone, int excludeId) {

        // On normalise le numéro de téléphone
        String normalizedPhone = Utils.normalizePhone(phone);

        for (Contact contact : contacts) {
            if (contact.getId() != excludeId && normalizedPhone.equals(contact.getPhoneNumber())) {
                return true;
            }
        }
        return false;
    }

    // Cette méthode permet de vérifier si un numéro de téléphone ou un email est déjà utilisé
    private boolean isPhoneOrEmailUsed(String phone, String email, int excludeId) {
        return isEmailUsed(email, excludeId) || isPhoneUsed(phone, excludeId);
    }

    // Cette méthode permet de modifer toutes les données d'un Contact. Celles-ci sont données en argument.
    public boolean updateContact(int id, String firstName, String lastName, String email, String phone, Tags tag) {

        // On récupère les données du contact.
        Contact contact = getContactById(id);
        if (contact == null) return false;

        // On regarde si l'email ou le téléphone est(sont) déjà pris.
        if (isPhoneOrEmailUsed(phone, email, id)) {
            System.out.println("Cet email ou ce numéro est déjà utilisé par un autre contact.");
            return false;
        }

        // Si tout est ok, on applique les modifications
        Utils.applyIfNotBlank(firstName, contact::setFirstName);
        Utils.applyIfNotBlank(lastName, contact::setLastName);
        Utils.applyIfNotBlank(email, contact::setEmail);
        Utils.applyIfNotBlank(phone, contact::setPhoneNumber);
        contact.setTag(tag);

        return true;
    }

    // Cette méthode retourne la liste de tous les contacts.
    public List<Contact> getAllContacts() {
        return new ArrayList<>(this.contacts);  
    }

    // On gère l'affiche d'un élément de type ContactManager
    @Override
    public String toString() {

        // S'il n'y a aucun contact, on affiche un message personnalisé.
        if (contacts.isEmpty()) {
            return "Aucun contact enregistré.";
        }

        // Sinon, on initialise une chaîne de caractères.
        StringBuilder listeDeContacts = new StringBuilder();

        // On affiche un message en haut de la liste de contacts.
        listeDeContacts.append("Carnet de contacts :");

        // Pour chaque contact...
        for (int i = 0; i < contacts.size(); i++) {

            // On passe à la ligne, on affiche le numéro du contact dans la liste et ses données (id, nom, prénom etc...)
            listeDeContacts.append("\n").append((i + 1)).append(". ").append(contacts.get(i));
        }
        return listeDeContacts.toString();
    }
}