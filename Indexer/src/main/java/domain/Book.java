package domain;

import java.sql.Date;

public class Book {

    private int id;
    private String title;
    private String author;
    private Date releaseDate;
    private String language;

    public Book(int id, String title, String author, Date releaseDate, String language) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.language = language;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String title() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String author() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date releaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String language() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

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
