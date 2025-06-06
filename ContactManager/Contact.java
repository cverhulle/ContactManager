package contactmanager;

// Cette classe a pour but de créer l'objet "Contact". 
public class Contact {

    // On fournit quatre propriétés à cette classe.
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;

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
    public String getPhoneNumber(){
        return phoneNumber;
    }

    // On crée une méthode pour modifier le nom.
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    // On crée une méthode pour modifier le prénom.
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    // On crée une méthode pour modifier l'email
    public void setEmail(String email) {
        this.email = email;
    }

    // On crée une méthode pour modifier le numéro de téléphone
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    // On gère l'affichage dans la console
    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + email + " - " + phoneNumber;
    }

    /*On redéfinit la méthode equals.
    @Override
    public boolean equals(Object obj) {

        // Si l'objet qu'on compare est exactement le même, on retourne true.
        if (this == obj) return true;

        // Si l'objet est null ou pas du même type, on retourne false.
        if (obj == null || getClass() != obj.getClass()) return false;

        // On transforme l'objet en contact pour accèder à ses propriétés.
        Contact contact = (Contact) obj;

        // On compare les deux emails en ignorant la casse
        return email.equalsIgnoreCase(contact.email); 
    }

    // On redéfinit la méthode hashCode pour qu'elle retourne un numéro unique pour chaque email.
    @Override
    public int hashCode() {
        return email.toLowerCase().hashCode();
    } */
}
