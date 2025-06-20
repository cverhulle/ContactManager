package contactmanager.io;

import contactmanager.core.Contact;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ContactIO {
    
    // On nomme le fichier
    private static final String FILE_NAME = "contacts.csv";

    // Sauvegarde la liste des contacts dans un fichier CSV
    public static void exportToCSV(List<Contact> contacts) {

        // On ajoute un try pour que le fichier soit correctement fermé
        // même en cas d'erreur.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            // Pour chaque contact
            for (Contact contact : contacts) {

                // On récupère ses données
                String line = String.format("%d,%s,%s,%s,%s,%s",
                        contact.getId(),
                        contact.getLastName(),
                        contact.getFirstName(),
                        contact.getEmail(),
                        contact.getPhoneNumber(),
                        contact.getTag().name());
                
                // On écrit la ligne dans le fichier
                writer.write(line);

                // On passe à la ligne suivante.
                writer.newLine();
            }

            // Message d'information à la fin de l'écriture du fichier.
            System.out.println("Contacts exportés avec succès !");
        
        // En cas d'erreur, on affiche un message.
        } catch (IOException e) {
            System.out.println("Erreur lors de l'export : " + e.getMessage());
        }
    }

}
