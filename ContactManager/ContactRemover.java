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

    // Cette méthode permet de supprimer un contact.
    private void removeContact () {

        // On demande à l'utilisateur l'id à rechercher.
        Integer id = askContactId("de suppression");

        // On gère les cas d'erreur
        if (id == null) return;

        // Si tout est ok, on appelle la méthode suivante.
        confirmRemoveContact(id);
    }

    // Cette méthode permet de confirmer la suppression d'un contact
    private void confirmRemoveContact(Integer id) {

        // On récupère le contact grâce à son id.
        Contact contact = contacts.getContactById(id);

        // S'il est trouvé...
        if (contact != null) {

            // On demande à l'utilisateur de confirmer la suppression
            String message = "Voulez-vous vraiment supprimer ce contact ? " + contact;

            // Si c'est confirmé...
            if (InputUtils.confirmChoice(scanner, message)) {

                // On supprime le contact.
                contacts.removeContactById(id);

            // Sinon, on annule la suppression.
            } else {
                System.out.println("Suppression annulée.");
            }
        } else {
            System.out.println("Aucun contact trouvé avec cet ID.");
        }
    }

    // Cette méthode permet de centraliser la demande d'un id de contact.
    private Integer askContactId(String action) {
        // On demande l'id de contact.
        try {
            return InputUtils.askIdContact(scanner);

        // Si l'utilisateur choisit d'annuler, on gère l'erreur
        } catch (UserCancelledException e) {
            System.out.println("Action " + action + " annulée.");
            return null;
        }
    }

}
