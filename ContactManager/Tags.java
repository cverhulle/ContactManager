package contactmanager;

public enum Tags {
    AMI,
    TRAVAIL,
    FAMILLE,
    AUTRE;


    public static String[] getTagsNamesInString() {
        Tags[] tags = values();
        String[] tagNames = new String[tags.length];
        for (int i = 0; i < tags.length; i++) {
            tagNames[i] = tags[i].toString();
        }
        return tagNames;
    }
}
