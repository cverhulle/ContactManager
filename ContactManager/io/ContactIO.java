package contactmanager.io;

import contactmanager.core.Contact;
import contactmanager.core.Tags;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactIO {

    private static final String AUTO_SAVE_PATH = "autosave.csv";
    
    // Sauvegarde la liste des contacts dans un fichier CSV
    public static void exportToCSV(List<Contact> contacts, String filePath) {

        // Bloc try-with-resources : ouvre automatiquement le fichier et le referme correctement
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

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

    // Méthode permettant d’importer des contacts depuis un fichier CSV
    public static List<Contact> importFromCSV(String filePath) {

        // On crée une liste vide qui stockera les contacts.
        List<Contact> importedContacts = new ArrayList<>();

        // Bloc try-with-resources : ouvre automatiquement le fichier et le referme correctement
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            // On lit le fichier ligne par ligne
            while ((line = reader.readLine()) != null) {

                // On divise chaque ligne sur la virgule 
                // (Cela suppose que chaque champ du contact est séparé par une virgule)
                String[] parts = line.split(",");

                // On vérifie que la ligne a bien 6 éléments (id, nom, prénom, email, téléphone, tag)
                if (parts.length != 6) {
                    System.out.println("Ligne ignorée (format invalide) : " + line);

                    // On passe à ligne suivante.
                    continue; 
                }

                try {
                    // Extraction et conversion des champs
                    int id = Integer.parseInt(parts[0].trim());             // id
                    String lastName = parts[1].trim();                      // nom
                    String firstName = parts[2].trim();                     // prénom
                    String email = parts[3].trim();                         // email
                    String phone = parts[4].trim();                         // téléphone
                    Tags tag = Tags.valueOf(parts[5].trim().toUpperCase()); // tag (doit exister dans l'enum Tags)

                    // Création d’un nouveau Contact à partir des données lues
                    Contact contact = new Contact(lastName, firstName, email, phone, tag);

                    // On fixe l'id à la main.
                    contact.setId(id); 

                    // Ajout à la liste
                    importedContacts.add(contact);

                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion numérique sur la ligne : " + line);
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Tag inconnu ou autre erreur sur la ligne : " + line);
                }
            }

            System.out.println("Contacts importés avec succès !");

        } catch (IOException e) {
            // Gère les erreurs d’ouverture ou de lecture du fichier
            System.out.println("Erreur lors de l'import : " + e.getMessage());
        }

        // On retourne tous les contacts.
        return importedContacts;
    }
}
