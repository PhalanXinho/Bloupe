package metadata;

import domain.Book;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MetadataManager {

    private final String metadata;

    public MetadataManager(String metadata) {
        this.metadata = metadata;
    }

    public Book bookFromMetadata(int id) {
        return new Book(id, extractTitle(), extractAuthor(), extractReleaseDate(), extractLanguage());
    }

    private String extractTitle() {
        return extractPattern(metadata, "Title: ");
    }

    private String extractLanguage() {
        return extractPattern(metadata, "Language: ");
    }

    private Date extractReleaseDate() {
        String date = extractPattern(metadata, "Release date: ");
        date = date.substring(0, date.indexOf(" ["));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date, formatter);
        return Date.valueOf(ld);
    }

    private String extractAuthor() {
        return extractPattern(metadata, "Author: ");
    }

    private String extractPattern(String text, String pattern) {
        int startIdx = text.indexOf(pattern) + pattern.length();
        int endIdx = text.indexOf('\n', startIdx);
        int endIdx2 = text.indexOf('\r', startIdx);
        return text.substring(startIdx, Math.min(endIdx, endIdx2));
    }
}
