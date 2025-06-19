package contactmanager.utils;

import java.util.ArrayList;
import java.util.List;

import contactmanager.Contact;
import contactmanager.Tags;

public class SearchUtils {

    // Cette méthode sera appelée dans les différentes voies pour trouver un contact.
    // Elle retourne la liste des contacts correspondant au critères.
    private static List<Contact> searchGeneric(List<Contact> contacts, java.util.function.Predicate<Contact> predicate) {
        List<Contact> foundContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (predicate.test(contact)) {
                foundContacts.add(contact);
            }
        }
        return foundContacts;
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut prénom.
    public static List<Contact> searchByFirstName(List<Contact> contacts, String query) {
        return searchGeneric(contacts, contact -> contact.getFirstName().toLowerCase().contains(query.toLowerCase()));
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut nom.
    public static List<Contact> searchByLastName(List<Contact> contacts, String query) {
        return searchGeneric(contacts, contact -> contact.getLastName().toLowerCase().contains(query.toLowerCase()));
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut email.
    public static List<Contact> searchByEmail(List<Contact> contacts, String query) {
        return searchGeneric(contacts, contact -> contact.getEmail().toLowerCase().contains(query.toLowerCase()));
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut phone.
    public static List<Contact> searchByPhone (List<Contact> contacts, String query) {
        String normalizedQuery = Utils.normalizePhone(query);
        return searchGeneric(contacts, contact -> contact.getPhoneNumber().replaceAll("\\s+", "").contains(normalizedQuery));
    }

    // Cette méthode retourne les contacts correspondants à la requete query dans l'attribut tag.
    public static List<Contact> searchByTag(List<Contact> contacts, Tags tag) {
        return searchGeneric(contacts, contact -> contact.getTag() == tag);
    }

}
