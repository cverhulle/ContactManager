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
}
