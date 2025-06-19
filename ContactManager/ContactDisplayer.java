package contactmanager;

import java.util.List;

public class ContactDisplayer {

    // Cette méthode permet d'afficher les Contacts dans une liste de contacts.
    private void displayContacts(List<Contact> contactsToDisplay) {
        if (contactsToDisplay.isEmpty()) {
            System.out.println("Aucun contact trouvé.");
        } else {
            contactsToDisplay.forEach(System.out::println);
        }
    }

}
