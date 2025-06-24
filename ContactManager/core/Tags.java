package contactmanager.core;

// Cette classe permet de lister les 4 catégories possibles pour un contact.
public enum Tags {
    AMI,
    TRAVAIL,
    FAMILLE,
    AUTRE;

    // Cette méthode renvoie la lsite des choix de Tags en tableau de String.
    public static String[] getTagsNamesInString() {
        Tags[] tags = values();
        String[] tagNames = new String[tags.length];
        for (int i = 0; i < tags.length; i++) {
            tagNames[i] = tags[i].toString();
        }
        return tagNames;
    }

    // Cette méthode prend une chaine et retourne le Tag correspondant ou NULL si inconnu.
    public static Tags parseTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return null;
        }
        try {
            return Tags.valueOf(tagName.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
