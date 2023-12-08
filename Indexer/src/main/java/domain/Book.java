package domain;

import java.sql.Date;

public record Book(int id, String title, String author, Date releaseDate, String language) {


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate=" + releaseDate +
                ", language='" + language + '\'' +
                '}';
    }
}
