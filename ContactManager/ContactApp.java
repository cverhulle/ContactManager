package contactmanager;

import java.util.Scanner;

// Cette classe permet de gérer le déroulé de l'application en fonction des réponses de l'utilisateur
public class ContactApp {
    @SuppressWarnings("FieldMayBeFinal")
    private ContactManager contacts;
    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;
    
    // On crée le constructeur de la classe : 
    // Il initialise une liste de contacts vide et, le scanner
    public ContactApp() {
        this.contacts = new ContactManager();
        this.scanner = new Scanner(System.in);
    }

    // Cette méthode est utilisée lorsque le programme est en cours
    public void run() {
        boolean running = true;

        // Tant que le programme est en cours, on affiche le menu.
        while(running) {
            // On affiche le menu principal
            String choice = displayMainMenu();

            // On ajout en fonction de la réponse de ce dernier.
            switch (choice) {
                case "1" -> addContact();
                case "2" -> removeContact();
                case "3" -> showAllContacts();
                case "4" -> findContact();
                case "5" -> modifyContact();
                case "6" -> {
                    running = false;
                    System.out.println("Au revoir !");
                }
                default -> System.out.println("Choix invalide.");
            }
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

        String choice = Utils.askMenuChoice(scanner, "--- MENU ---", options);
        return choice;
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
            contacts.removeContactById(id);
        }
    }

    // Cette méthode permet d'afficher les contacts.    
    private void showAllContacts() {
        System.out.println(contacts);
    }

    // Cette méthode permet de recherche un contact
    private void findContact() {
        System.out.println("Rechercher par :");
        System.out.println("1. Prénom");
        System.out.println("2. Nom");
        System.out.println("3. Email");
        System.out.println("4. Téléphone");
        System.out.print("Votre choix : ");
        String fieldChoice = scanner.nextLine();

        System.out.print("Entrez la valeur à rechercher : ");
        String query = scanner.nextLine();

        switch (fieldChoice) {
            case "1" -> contacts.searchByFirstName(query);
            case "2" -> contacts.searchByLastName(query);
            case "3" -> contacts.searchByEmail(query);
            case "4" -> contacts.searchByPhone(query);
            default -> System.out.println("Choix invalide.");
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

        boolean success = contacts.updateContact(id, firstName, lastName, email, phone);
        System.out.println(success ? "Contact modifié avec succès !" : "Erreur lors de la modification.");
    }
}
