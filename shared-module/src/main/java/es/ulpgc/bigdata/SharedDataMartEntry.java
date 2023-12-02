package es.ulpgc.bigdata;

public class SharedDataMartEntry {

    private int bookId;
    private int appearance;

    public SharedDataMartEntry(int bookId, int appearance) {
        this.bookId = bookId;
        this.appearance = appearance;
    }

    public int bookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int appearance() {
        return appearance;
    }

    public void setAppearance(int appearance) {
        this.appearance = appearance;
    }
}