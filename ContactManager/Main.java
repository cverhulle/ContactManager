package contactmanager;

public class Main {

    public static void main(String[] args) {
        Contact contact1 = new Contact("Jean", "Dupont", "jean.dupont@gmail.com", "0321498623");
        Contact contact2 = new Contact("Jean", "Dupont", "jeanne.dupont@gmail.com", "03 21 49 86 20");
        Contact contact3 = new Contact("Jean", "Dupont", "j.dupont@gmail.com", "03 21 49 86 23");

        ContactManager contacts = new ContactManager();

        contacts.addContact(contact1); // Le contact est ajouté
        contacts.addContact(contact2); // Idem
        contacts.addContact(contact3); // Le contact n'est pas ajouté (numéro de téléphone)
        
        System.out.println(contacts);

        contacts.removeContactById(1); // Supprime l'id numéro 1
        contacts.removeContactById(3); // Ne fait rien

        System.out.println(contacts);


    }
}
