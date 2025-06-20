package contactmanager.core;

import contactmanager.utils.Utils;

// Cette classe a pour but de créer l'objet "Contact". 
public class Contact {

    // On crée un compteur pour les id.
    // Le "static" permet de faire en sorte que le compteur soit commun à tous les contacts.
    private static int idCounter = 1; 

    // On fournit six propriétés à cette classe.
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private int id;
    private Tags tag;

    // On crée le constructeur de la classe Contact.
    public Contact(String lastName, String firstName, String email, String phoneNumber, Tags tag) {
        this.id = idCounter++;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = Utils.normalizePhone(phoneNumber);
        this.tag = tag;
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

    // On crée une méthode pour récupérer l'id.
    public int getId() {
        return id;
    }

    // On crée une méthode pour récupérer le tag.
    public Tags getTag() {
        return tag;
    }

    // On crée une méthode pour modifier l'id (utiliser dans l'export de fichier)
    public void setId(Integer id) {
        this.id = id;
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

    // On crée une méthode pour modifier le tag
    public void setTag(Tags tag) {
        this.tag = tag;
    }

    // On crée une méthode pour modifier le numéro de téléphone
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = Utils.normalizePhone(phoneNumber);
    }

    // On gère l'affichage dans la console
    @Override
    public String toString() {
        return "[" + id + "] " + firstName + " " + lastName + " - " + email + " - " + phoneNumber + " - " + tag;
    }
}
