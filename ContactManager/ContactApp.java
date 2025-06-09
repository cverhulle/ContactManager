package contactmanager;

// Cette classe permet de gérer le déroulé de l'application en fonction des réponses de l'utilisateur

import java.util.Scanner;

public class ContactApp {
    private ContactManager contacts;
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
        }
    }
}
