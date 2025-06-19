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
            case "3" -> handleDisplayContacts();
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
    private void handleDisplayContacts(){

        // Tout d'abord, on affiche tous les contacts.
        displayer.displayContacts(contacts.getAllContacts());

        // On demande à l'utilisateur s'il souhaite trier la liste
        List<Contact> sortedContacts = sorter.handleSortingContact(contacts.getAllContacts());
        
        // On affiche la lsite des contacts triées.
        displayer.displayContacts(sortedContacts);
    }

    // Cette méthode permet de rechercher un contact
    private void findContact() {

        // On gère la recherche de contact avec un filtre.
        List<Contact> contactsFound = searcher.handleFindingContact();

        // On affiche la liste trouvée
        displayer.displayContacts(contactsFound);

        // On demande à l'utilisateur s'il veut trier le résultat
        List<Contact> sortedContacts = sorter.handleSortingContact(contactsFound);
        displayer.displayContacts(sortedContacts);
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
        // On lance la méthode pour lancer la modification.
        handleModificationContact(id, contact);
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

    // Cette méthode permet de créer un contact à partir des entrées utilisateur.
    private Contact buildContactFromUserInput(Contact existingContact) throws UserCancelledException {
        String firstName = InputUtils.askField(scanner, "Prénom", 
                                        existingContact == null ? null : existingContact.getFirstName(), 
                                        existingContact != null);
        String lastName = InputUtils.askField(scanner, "Nom", 
                                        existingContact == null ? null : existingContact.getLastName(), 
                                        existingContact != null);
        String email = InputUtils.askField(scanner, "Email", 
                                        existingContact == null ? null : existingContact.getEmail(), 
                                        existingContact != null);
        String phone = InputUtils.askField(scanner, "Téléphone", 
                                        existingContact == null ? null : existingContact.getPhoneNumber(), 
                                        existingContact != null);
        Tags tag = InputUtils.askTag(scanner, "Tag", 
                                        existingContact == null ? null : existingContact.getTag(), 
                                        existingContact != null);

        return new Contact(lastName, firstName, email, phone, tag);
    }

    // Cette méthode retourne la liste de contacts entières triés selon le choix et dans l'ordre donné par ascending.
    private List<Contact> getSortedContactsByChoice(List<Contact> inputList, String choice, boolean ascending) {
        return switch (choice) {
            case "1" -> SortUtils.getContactsSortedByFirstName(inputList, ascending);
            case "2" -> SortUtils.getContactsSortedByLastName(inputList, ascending);
            case "3" -> SortUtils.getContactsSortedByEmail(inputList, ascending);
            case "4" -> SortUtils.getContactsSortedByPhoneNumber(inputList, ascending);
            case "5" -> SortUtils.getContactsSortedByTag(inputList, ascending);
            default -> inputList;
        };
    }

    // Cette méthode permet de gérer la modification de contact.
    private void handleModificationContact(Integer id, Contact contact) {

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

        } // Si l'utlisateur déclenche l'erreur (en tapant 0), on annule la modification et, on affiche un message.
        catch (UserCancelledException e) {
            System.out.println("Modification annulée");
        }
    }

    // Cette méthode permet de gérer le tri d'une liste de contact.
    private List<Contact> handleSortingContact(List<Contact> inputList) {
        // On initialise les variables pour le tri
        String[] options = { "Prénom", "Nom", "Email", "Téléphone", "Tag"};

        // On demande à l'utilisateur s'il veut trier ou revenir en arrière
        System.out.println("\nVoulez-vous trier les résultats ?");

        try{
            // On demande sur quel attribut s'effectuera le tri
            String choice = InputUtils.askMenuChoice(scanner, "Choisissez le filtre pour trier les données", options, "Quitter", false);

            // On demande si le tri est croissant ou décroissant
            boolean ascending = InputUtils.askSortDirectionInBoolean(scanner);

            // En fonction des choix réalisés, on appelle la méthode correspondante.
            return getSortedContactsByChoice(inputList, choice, ascending);
        }  
        // Si l'utlisateur déclenche l'erreur (en tapant 0), on annule la recherche et, on affiche un message.
        catch (UserCancelledException e) {
            System.out.println("Retour au menu principal.");
            return inputList;
        }
    }
}
