package domain;

public class Book {

    private int id;
    private String title;
    private String author;
    private int year;
    private String language;
    private String originalPublication;

    public Book(int id, String title, String author, int year, String language, String originalPublication) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.language = language;
        this.originalPublication = originalPublication;
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

    public int year() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String language() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String originalPublication() {
        return originalPublication;
    }

    public void setOriginalPublication(String originalPublication) {
        this.originalPublication = originalPublication;
    }
}
