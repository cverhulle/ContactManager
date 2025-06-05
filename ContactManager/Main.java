package contactmanager;

public class Main {

    public static void main(String[] args) {
        Contact contact1 = new Contact("Jean", "Dupont", "jean.dupont@gmail.com", "0321498623");
        Contact contact2 = new Contact("Jean", "Dupond", "jean.dupond@gmail.com", "0321126873");

        ContactManager contacts = new ContactManager();

        contacts.addContact(contact1);
        contacts.addContact(contact2);

        System.out.println(contacts);
    }
}
