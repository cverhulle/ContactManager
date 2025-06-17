package contactmanager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

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

    // Cette méthode sera appelée dans les différentes voies pour trouver un contact.
    // Elle retourne la liste des contacts correspondant au critères.
    private List<Contact> searchGeneric(java.util.function.Predicate<Contact> predicate) {
        List<Contact> foundContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (predicate.test(contact)) {
                foundContacts.add(contact);
            }
        }
        return foundContacts;
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut prénom.
    public List<Contact> searchByFirstName(String query) {
        return searchGeneric(contact -> contact.getFirstName().toLowerCase().contains(query.toLowerCase()));
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut nom.
    public List<Contact> searchByLastName(String query) {
        return searchGeneric(contact -> contact.getLastName().toLowerCase().contains(query.toLowerCase()));
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut email.
    public List<Contact> searchByEmail(String query) {
        return searchGeneric(contact -> contact.getEmail().toLowerCase().contains(query.toLowerCase()));
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut phone.
    public List<Contact> searchByPhone(String query) {
        String normalizedQuery = Utils.normalizePhone(query);
        return searchGeneric(contact -> contact.getPhoneNumber().replaceAll("\\s+", "").contains(normalizedQuery));
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut tag.
    public List<Contact> searchByTag(Tags tag) {
        return searchGeneric(contact -> contact.getTag() == tag);
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

    // Cette méthode retourne une liste triée de contacts selon le comparateur donné.
    public List<Contact> getContactsSortedBy(Comparator<Contact> comparator) {
        List<Contact> sortedList = new ArrayList<>(contacts);
        sortedList.sort(comparator);
        return sortedList;
    }

    // Cette méthode permet de trier une liste en fournissant le critère d'extraction et, l'ordre de tri.
    public List<Contact> getContactsSortedByField(Function<Contact, ?> keyExtractor, boolean ascending) {
        @SuppressWarnings("unchecked")
        Comparator<Contact> comparator = Comparator.comparing(
            contact -> (Comparable<Object>) keyExtractor.apply(contact),
            Comparator.nullsLast(Comparator.naturalOrder())
        );
        if (!ascending) {
            comparator = comparator.reversed();
        }
        return getContactsSortedBy(comparator);
    }

    // Cette méthode retourne une liste triée de contacts selon le prénom.
    // Le paramètre indique si le tri est par ordre alphbaétique ou anti-alphabétique.
    public List<Contact> getContactsSortedByFirstName(boolean ascending) {
        Comparator<Contact> comparator = Comparator.comparing(Contact::getFirstName, String.CASE_INSENSITIVE_ORDER);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        return getContactsSortedBy(comparator);
    }

    // Cette méthode retourne une liste triée de contacts selon le nom.
    // Le paramètre indique si le tri est par ordre alphbaétique ou anti-alphabétique.
    public List<Contact> getContactsSortedByLastName(boolean ascending) {
        Comparator<Contact> comparator = Comparator.comparing(Contact::getLastName, String.CASE_INSENSITIVE_ORDER);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        return getContactsSortedBy(comparator);
    }

    // Cette méthode retourne une liste triée de contacts selon le Tag.
    // Le paramètre indique si le tri est par ordre alphbaétique ou anti-alphabétique.
    public List<Contact> getContactsSortedByTag(boolean ascending) {
        Comparator<Contact> comparator = Comparator.comparing(Contact::getTag);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        return getContactsSortedBy(comparator);
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
