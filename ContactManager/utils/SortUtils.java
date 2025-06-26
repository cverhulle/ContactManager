package contactmanager.utils;

import contactmanager.core.Contact;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

// Cette classe regroupe les méthodes liées au tri.
public class SortUtils {

    // Cette méthode retourne une liste triée de contacts selon le comparateur donné.
    public static List<Contact> getContactsSortedBy(List<Contact> inputList, Comparator<Contact> comparator) {
        List<Contact> sortedList = new ArrayList<>(inputList);
        sortedList.sort(comparator);
        return sortedList;
    }

    // Cette méthode permet de trier une liste en fournissant le critère d'extraction et, l'ordre de tri.
    public static List<Contact> getContactsSortedByField(List<Contact> inputList, Function<Contact, ?> keyExtractor, boolean ascending) {
        @SuppressWarnings("unchecked")

        // On crée un comparateur basé sur la clé extraite de chaque contact, que l'on caste en Comparable<Object>
        Comparator<Contact> comparator = Comparator.comparing(
            contact -> (Comparable<Object>) keyExtractor.apply(contact),    // Extraction de la clé et cast en Comparable
            Comparator.nullsLast(Comparator.naturalOrder())                 // Gestion des valeurs null en les plaçant à la fin, ordre naturel pour le reste
        );

        // Si on veut un tri décroissant (ordre anti-alphabétique), on inverse le comparateur
        if (!ascending) {
            comparator = comparator.reversed();
        }
        return getContactsSortedBy(inputList, comparator);
    }

    // Cette méthode retourne une liste triée de contacts selon le prénom.
    // Le paramètre indique si le tri est par ordre alphbaétique ou anti-alphabétique.
    public static List<Contact> getContactsSortedByFirstName(List<Contact> inputList, boolean ascending) {
        return getContactsSortedByField(inputList, Contact::getFirstName, ascending);
    }

    // Cette méthode retourne une liste triée de contacts selon le nom.
    // Le paramètre indique si le tri est par ordre alphbaétique ou anti-alphabétique.
    public static List<Contact> getContactsSortedByLastName(List<Contact> inputList, boolean ascending) {
        return getContactsSortedByField(inputList, Contact::getLastName, ascending);
    }

    // Cette méthode retourne une liste triée de contacts selon l'email.
    // Le paramètre indique si le tri est par ordre alphbaétique ou anti-alphabétique.
    public static List<Contact> getContactsSortedByEmail(List<Contact> inputList, boolean ascending) {
        return getContactsSortedByField(inputList, Contact::getEmail, ascending);
    }

    // Cette méthode retourne une liste triée de contacts selon le numéro de téléphone.
    // Le paramètre indique si le tri est par ordre alphbaétique ou anti-alphabétique.
    public static List<Contact> getContactsSortedByPhoneNumber(List<Contact> inputList, boolean ascending) {
        return getContactsSortedByField(inputList, Contact::getPhoneNumber, ascending);
    }

    // Cette méthode retourne une liste triée de contacts selon le Tag.
    // Le paramètre indique si le tri est par ordre alphbaétique ou anti-alphabétique.
    public static List<Contact> getContactsSortedByTag(List<Contact> inputList, boolean ascending) {
        return getContactsSortedByField(inputList, contact -> contact.getTag().name(), ascending);
    }
}