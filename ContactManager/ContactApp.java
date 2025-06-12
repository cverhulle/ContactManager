package contactmanager;

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

    // Cette méthode permet d'afficher le menu principal. Elle retourne le chix de l'utilisateur.
    private String displayMainMenu() {
        String[] options = {
            "Ajouter un contact",
            "Supprimer un contact",
            "Afficher les contacts",
            "Rechercher un contact",
            "Modifier un contact",
            "Quitter"
        };

        String choice = Utils.askMenuChoice(scanner, "--- MENU ---", options, null);
        return choice;
    }

    // Cette méthode prend en argument le numéro du choix de l'utilisateur et appel la méthode correspondante.
    private boolean handleMenuChoice(String choice) {

        // Si le choix n'est pas valide, on retourne True
        if (!Utils.isValidMenuChoice(choice, 6)) {
            System.out.println("Choix invalide.");
            return true;
        }

        switch (choice) {
            case "1" -> addContact();
            case "2" -> removeContact();
            case "3" -> showAllContacts();
            case "4" -> findContact();
            case "5" -> modifyContact();
            case "6" -> {
                System.out.println("Au revoir !");
                return false;
            }
            default -> System.out.println("Choix invalide.");
        }
        return true;
    }

    // Cette métohde permet d'ajouter un contact dans la liste.
    private void addContact() {
        String firstName = Utils.askField(scanner, "Prénom", null, false);
        String lastName  = Utils.askField(scanner, "Nom", null, false);
        String email     = Utils.askField(scanner, "Email", null, false);
        String phone     = Utils.askField(scanner, "Téléphone", null, false);

        Contact contact = new Contact(lastName, firstName, email, phone);
        contacts.addContact(contact);
    }

    // Cette méthode permet de supprimer un contact.
    private void removeContact() {
        int id = Utils.askIdContact(scanner);
        if (id != -1) {
            confirmRemoveContact(id);
        }
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

    // Cette méthode permet d'afficher les contacts.    
    private void showAllContacts() {
        System.out.println(contacts);
    }

    // Cette méthode permet de recherche un contact
    private void findContact() {

        // On affiche la liste des champs de recherche possible.
        String[] options = { "Prénom", "Nom", "Email", "Téléphone" };
        String fieldChoice = Utils.askMenuChoice(scanner, "Rechercher par :", options, "Annuler la recherche");

        // Si le choix entré n'est pas dans les possibilités, on retourne au menu principal.
        if (!Utils.isValidMenuChoice(fieldChoice, options.length)) {
            System.out.println("Choix invalide.");
            return;
        }

        // On demande la chaîne de caractères à trouver.
        String query = Utils.askInput(scanner, "Entrez la valeur à rechercher : ");

        // On effectue la recherche correspondate au choix et à la chaîne entrée.
        switch (fieldChoice) {
            case "1" -> contacts.searchByFirstName(query);
            case "2" -> contacts.searchByLastName(query);
            case "3" -> contacts.searchByEmail(query);
            case "4" -> contacts.searchByPhone(query);
        }
    }

    // Cette méthode permet de modifier un Contact.
    private void modifyContact() {

        // On récupère l'id du contact à modifier
        int id = Utils.askIdContact(scanner);
        if (id == -1) return;

        // On récupère les données du contact à modifier
        Contact contact = contacts.getContactById(id);
        if (contact == null) {
            System.out.println("Aucun contact trouvé avec cet ID.");
            return;
        }

        // On entre les données à modifier
        System.out.println("Laissez vide pour ne pas modifier un champ.");

        String firstName = Utils.askField(scanner, "Nouveau prénom", contact.getFirstName(), true);
        String lastName  = Utils.askField(scanner, "Nouveau nom", contact.getLastName(), true);
        String email     = Utils.askField(scanner, "Nouvel email", contact.getEmail(), true);
        String phone     = Utils.askField(scanner, "Nouveau téléphone", contact.getPhoneNumber(), true);

        // On met à jour le contact avec les données.
        boolean success = contacts.updateContact(id, firstName, lastName, email, phone);

        // Si les données sont "valables", on affiche un message de succès. Sinon, c'est un message d'erreur.
        System.out.println(success ? "Contact modifié avec succès !" : "Erreur lors de la modification.");
    }
}
