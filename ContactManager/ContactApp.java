package contactmanager;

import java.util.List;
import java.util.Scanner;

// Cette classe permet de gérer le déroulé de l'application en fonction des réponses de l'utilisateur.
public class ContactApp {

    @SuppressWarnings("FieldMayBeFinal")
    private ContactManager contacts;
    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;
    
    // On crée le constructeur de la classe : 
    // Il initialise une liste de contacts vide et, le scanner.
    public ContactApp() {
        this.contacts = new ContactManager();
        this.scanner = new Scanner(System.in);
    }

    // Cette méthode est utilisée lorsque le programme est en cours.
    public void run() {
        boolean running = true;

        // Tant que le programme est en cours, on affiche le menu.
        while(running) {
            // On affiche le menu principal et, on récupère son choix.
            String choice = displayMainMenu();

            // On appelle la méthode correspondante au choix de l'utilisateur.
            running = handleMenuChoice(choice);
        }
        scanner.close();
    }

    // Cette méthode permet d'afficher le menu principal. Elle retourne le choix de l'utilisateur.
    private String displayMainMenu() {
        String[] options = {
            "Ajouter un contact",
            "Supprimer un contact",
            "Afficher les contacts",
            "Rechercher un contact",
            "Modifier un contact",
            "Quitter"
        };

        String choice = Utils.askMenuChoice(scanner, "--- MENU ---", options, null,false);
        return choice;
    }

    // Cette méthode prend en argument le numéro du choix de l'utilisateur et appelle la méthode correspondante.
    private boolean handleMenuChoice(String choice) {

        // En fonction du choix réalisé, on appelle la méthode correspondante.
        switch (choice) {
            case "1" -> addContact();
            case "2" -> removeContact();
            case "3" -> displayContacts(contacts.getAllContacts());
            case "4" -> findContact();
            case "5" -> modifyContact();
            case "6" -> {
                System.out.println("Au revoir !");
                return false;
            }
        }

        // On retourne true pour poursuivre la boucle.
        return true;
    }

    // Cette métohde permet d'ajouter un contact dans la liste.
    private void addContact() {

        // On demande les champs du contact à l'utilisateur
        try{ 
            Contact newContact = buildContactFromUserInput(null);
            contacts.addContact(newContact);
        
        // Si l'utilisateur tape 0, on récupère l'erreur et on annule l'ajout.
        } catch (CancelledInputException e) {
            System.out.println("Ajout annulé.");
        }
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
            if (Utils.confirmChoice(scanner, message)) {

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

    // Cette méthode permet d'afficher les Contacts dans une liste de contacts.
    private void displayContacts(List<Contact> contactsToDisplay) {
        if (contactsToDisplay.isEmpty()) {
            System.out.println("Aucun contact trouvé.");
        } else {
            contactsToDisplay.forEach(System.out::println);
        }
    }

    // Cette méthode permet de rechercher un contact lorsqu'on est dans le cas Tag
    private void findContactByTag() {

        // On affiche toutes les options de tag
        Tags tagChoice = Utils.askTag(scanner, "Choisissez un tag :", null, true);

        // Si l'entrée est vide, on affiche tous les contacts.
        if (tagChoice == null) {
            displayContacts(contacts.getAllContacts());
        } else {
            // Sinon, on affiche les résultats.
            displayContacts(contacts.searchByTag(tagChoice));
        }
    }

    // Cette méthode permet de recherche un contact
    private void findContact() {

        // On affiche la liste des champs de recherche possible.
        String[] options = { "Prénom", "Nom", "Email", "Téléphone", "Tag" };

        try{ 
            String fieldChoice = Utils.askMenuChoice(scanner, "Rechercher par :", options, "Annuler la recherche",false);

            // Si on choisit de chercher par Tags.
            if ("5".equals(fieldChoice)) { 
                findContactByTag();
            
            // Si l'option Tag n'est pas chosie
            } else {

                // On demande la donnée à rechercher
                String query = Utils.askInput(scanner, "Entrez la valeur à rechercher : ");

                // On lance la recherche correspondate
                switch (fieldChoice) {
                    case "1" -> displayContacts(contacts.searchByFirstName(query));
                    case "2" -> displayContacts(contacts.searchByLastName(query));
                    case "3" -> displayContacts(contacts.searchByEmail(query));
                    case "4" -> displayContacts(contacts.searchByPhone(query));
                }
            }
        }  // Si l'utlisateur déclenche l'erreur (en tapant 0), on annule la recherche et, on affiche un message.
        catch (CancelledInputException e) {
            System.out.println("Recherche annulée");
        }
    }

    // Cette méthode permet de modifier un Contact.
    private void modifyContact() {

        // On récupère l'id du contact à modifier.
        Integer id = askContactId("de modification");

        if (id == null) return;

        // On récupère les données du contact à modifier
        Contact contact = contacts.getContactById(id);
        if (contact == null) {
            System.out.println("Aucun contact trouvé avec cet ID.");
            return;
        }
        
        try{
            // On entre les données à modifier
            System.out.println("Laissez vide pour ne pas modifier un champ.");

            // On crée un contact mis à jour à partir des données du contact précédent.
            Contact updated = buildContactFromUserInput(contact);

            // On met à jour le contact avec les données.
            boolean success = contacts.updateContact(id, updated.getFirstName(), updated.getLastName(), 
                                                        updated.getEmail(), updated.getPhoneNumber(), updated.getTag()); 

            // Si les données sont "valables", on affiche un message de succès. Sinon, c'est un message d'erreur.
            System.out.println(success ? "Contact modifié avec succès !" : "Erreur lors de la modification.");
            
        // Si l'utlisateur déclenche l'erreur (en tapant 0), on annule la modification et, on affiche un message.
        } catch (CancelledInputException e) {
            System.out.println("Modification annulée");
        }
    }

    // Cette méthode permet de centraliser la demande d'un id de contact.
    private Integer askContactId(String action) {
        // On demande l'id de contact.
        try {
            return Utils.askIdContact(scanner);

        // Si l'utilisateur choisit d'annuler, on gère l'erreur
        } catch (CancelledInputException e) {
            System.out.println("Action " + action + " annulée.");
            return null;
        }
    }

    // Cette méthode permet de créer un contact à partir des entrées utilisateur.
    private Contact buildContactFromUserInput(Contact existingContact) throws CancelledInputException {
        String firstName = Utils.askField(scanner, "Prénom", 
                                        existingContact == null ? "" : existingContact.getFirstName(), 
                                        existingContact == null);
        String lastName = Utils.askField(scanner, "Nom", 
                                        existingContact == null ? "" : existingContact.getLastName(), 
                                        existingContact == null);
        String email = Utils.askField(scanner, "Email", 
                                        existingContact == null ? "" : existingContact.getEmail(), 
                                        existingContact == null);
        String phone = Utils.askField(scanner, "Téléphone", 
                                        existingContact == null ? "" : existingContact.getPhoneNumber(), 
                                        existingContact == null);
        Tags tag = Utils.askTag(scanner, "Tag", 
                                        existingContact == null ? null : existingContact.getTag(), 
                                        existingContact == null);

        return new Contact(lastName, firstName, email, phone, tag);
    }
}
