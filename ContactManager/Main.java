package contactmanager;

public class Main {

    public static void main(String[] args) {

        // Création de contacts.
        Contact contact1 = new Contact("Jean", "Dupont", "jean.dupont@gmail.com", "0321498623");
        Contact contact2 = new Contact("Jean", "Dupont", "jeanne.dupont@gmail.com", "03 21 49 86 20");
        Contact contact3 = new Contact("Jean", "Dupont", "j.dupont@gmail.com", "03 21 49 86 23");

        // On crée une liste de contact.
        ContactManager contacts = new ContactManager();

        // On ajoute des contacts
        contacts.addContact(contact1); // Le contact est ajouté
        contacts.addContact(contact2); // Idem
        contacts.addContact(contact3); // Le contact n'est pas ajouté (numéro de téléphone)
        
        // On affiche la liste
        System.out.println(contacts);

        // On teste la suppression de contact.
        contacts.removeContactById(1); // Supprime l'id numéro 1
        contacts.removeContactById(3); // Ne fait rien

        //On affiche la liste de contact.
        System.out.println(contacts);


    }
}
