package contactmanager;

import java.util.List;
import java.util.Scanner;

// Cette classe permet de gérer le déroulé de l'application en fonction des réponses de l'utilisateur.
public class ContactApp {

    // Classe Contact et Scanner.
    private final ContactManager contacts;
    private final Scanner scanner;

    // Modules métiers.
    private final ContactAdder adder;
    private final ContactRemover remover;
    private final ContactModifier modifier;
    private final ContactSearcher searcher;
    private final ContactDisplayer displayer;
    private final ContactSorter sorter;
    
    // On crée le constructeur de la classe : 
    // Il initialise une liste de contacts vide, le scanner et les instances de chaque classe.
    public ContactApp() {
        this.contacts = new ContactManager();
        this.scanner = new Scanner(System.in);

        this.adder = new ContactAdder(contacts, scanner);
        this.remover = new ContactRemover(contacts, scanner);
        this.modifier = new ContactModifier(contacts, scanner);
        this.searcher = new ContactSearcher(contacts, scanner);
        this.displayer = new ContactDisplayer();
        this.sorter = new ContactSorter(scanner);
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

        String choice = InputUtils.askMenuChoice(scanner, "--- MENU ---", options, null,false);
        return choice;
    }

    // Cette méthode prend en argument le numéro du choix de l'utilisateur et appelle la méthode correspondante.
    private boolean handleMenuChoice(String choice) {

        // En fonction du choix réalisé, on appelle la méthode correspondante.
        switch (choice) {
            case "1" -> adder.addContact();
            case "2" -> remover.removeContact();
            case "3" -> displayContacts();
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

    // Cette méthode permet de gérer le choix "Afficher les contacts"
    private void displayContacts(){

        // Tout d'abord, on affiche tous les contacts.
        displayer.displayContacts(contacts.getAllContacts());

        // On demande à l'utilisateur s'il souhaite trier la liste
        List<Contact> sortedContacts = sorter.sortContact(contacts.getAllContacts());
        
        // On affiche la lsite des contacts triées.
        displayer.displayContacts(sortedContacts);
    }

    // Cette méthode permet de rechercher un contact
    private void findContact() {

        // On gère la recherche de contact avec un filtre.
        List<Contact> contactsFound = searcher.findContact();

        // On affiche la liste trouvée
        displayer.displayContacts(contactsFound);

        // On demande à l'utilisateur s'il veut trier le résultat
        List<Contact> sortedContacts = sorter.sortContact(contactsFound);
        displayer.displayContacts(sortedContacts);
    }

    // Cette méthode permet de modifier un Contact.
    private void modifyContact() {

        // On lance la méthode pour lancer la modification.
        modifier.modifyContact();
    }
}
