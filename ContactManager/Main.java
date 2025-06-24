package contactmanager;

import contactmanager.app.ContactApp;

// Fichier principal de l'application.
// S'occupe de lancer le programme.
public class Main {

    public static void main(String[] args) {
        ContactApp app = new ContactApp();
        app.run();
    }
}
