package contactmanager;

public class UserCancelledException extends RuntimeException {
    public UserCancelledException() {
        super("Entrée annulée par l'utilisateur.");
    }
}