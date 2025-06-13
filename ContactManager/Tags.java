package contactmanager;

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
}
