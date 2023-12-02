package bookmanager;

public class FileContentManager {

    private final String CONTENT_START_PATTERN = "*** START OF THE PROJECT GUTENBERG";

    private final String METADATA_START_PATTERN = "Title:";

    private final String CONTENT_END_PATTERN = "*** END OF THE PROJECT GUTENBERG";


    private final String bookText;

    public FileContentManager(String bookText) {
        this.bookText = bookText;
    }

    public String getMetadataPart() {
        return bookText.substring(findMetadataStartIndex(bookText), findMetadataEndIndex(bookText));
    }

    public String getContentPart() {
        return bookText.substring(findContentStartIndex(bookText), findContentEndIndex(bookText));
    }

    private int findContentStartIndex(String bookText) {
        int patternStart = bookText.indexOf(CONTENT_START_PATTERN);
        int patternEnd = patternStart + CONTENT_START_PATTERN.length();
        bookText = bookText.substring(patternEnd);
        return patternEnd + bookText.indexOf("***") + 3;
    }

    private int findContentEndIndex(String bookText) {
        return bookText.indexOf(CONTENT_END_PATTERN);
    }

    private int findMetadataEndIndex(String bookText) {
        return bookText.indexOf(CONTENT_START_PATTERN);
    }

    private int findMetadataStartIndex(String bookText) {
        return bookText.indexOf(METADATA_START_PATTERN);
    }


}
