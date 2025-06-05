package contactmanager;

public class Main {

    public static void main(String[] args) {
        Contact contact = new Contact("Jean", "Dupont", "jean.dupont@gmail.com", "0321498623");
        System.out.println(contact);

        ContactManager contacts = new ContactManager();

        Contact contact2 = new Contact("Jean", "Dupond", "jean.dupond@gmail.com", "0321126873");
        contacts.addContact(contact2);
    }
}
