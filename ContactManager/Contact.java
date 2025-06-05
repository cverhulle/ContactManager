package contactmanager;

// Cette classe a pour but de créer l'objet "Contact". 
public class Contact {

    // On fournit quatre propriétés à cette classe.
    private String lastName;
    final private String firstName;
    final private String email;
    final private String phoneNumber;

    // On crée le constructeur de la classe Contact.
    public Contact(String lastName, String firstName, String email, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // On crée une méthode pour récupérer le nom.
    public String getLastName() {
        return lastName;
    }

    // On crée une méthode pour récupérer le prénom.
    public String getFirstName() {
        return firstName;
    }

    // On crée une méthode pour récupérer l'email.
    public String getEmail(){
        return email;
    }

    // On crée une méthode pour récupérer le numéro de téléphone.
    public String getPhoneNomber(){
        return phoneNumber;
    }

    // On crée une méthode pour modifier le nom.
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    // On gère l'affichage dans la console
    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + email + " - " + phoneNumber;
    }

    
}
