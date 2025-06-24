package contactmanager.exception;

// Cette classe a pour but de gérer l'entrée "0" du programme.
// Celle-ci permet d'annuler la saisie et de retourner au menu principal.
public class UserCancelledException extends RuntimeException {
    public UserCancelledException() {
        super("Entrée annulée par l'utilisateur.");
    }
}