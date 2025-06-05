package contactmanager;

// Cette classe a pour but de créer l'objet "Contact". 
public class Contact {

    // On fournit quatre propriétés à cette classe.
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;

    // On crée le constructeur de la classe Contact
    public Contact(String lastName, String firstName, String email, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
