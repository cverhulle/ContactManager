package contactmanager;

public class CancelledInputException extends RuntimeException {
    public CancelledInputException() {
        super("Entrée annulée par l'utilisateur.");
    }
}