package contactmanager;

// Cette classe permet de gérer le déroulé de l'application en fonction des réponses de l'utilisateur

import java.util.Scanner;

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
            System.out.println("\n--- MENU ---");
            System.out.println("1. Ajouter un contact");
            System.out.println("2. Supprimer un contact");
            System.out.println("3. Afficher les contacts");
            System.out.println("4. Quitter");
            System.out.print("Choix : ");

            // On récupère la réponse de l'utilisateur
            String choice = scanner.nextLine();

            // On ajout en fonction de la réponse de ce dernier.
            switch (choice) {
                case "1" -> addContact();
                case "2" -> removeContact();
                case "3" -> System.out.println(contacts);
                case "4" -> {
                    running = false;
                    System.out.println("Au revoir !");
                }
                default -> System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }

    // Cette métohde permet d'ajouter un contact dans la liste.
    private void addContact() {
        System.out.print("Prénom : ");
        String firstName = scanner.nextLine();
        System.out.print("Nom : ");
        String lastName = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Téléphone : ");
        String phone = scanner.nextLine();

        Contact contact = new Contact(lastName, firstName, email, phone);
        contacts.addContact(contact);
    }

    // Cette méthode permet de supprimer un contact.
    private void removeContact() {
        System.out.print("ID du contact à supprimer : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            contacts.removeContactById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
        }
    }

}
