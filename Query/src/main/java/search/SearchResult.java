package search;

import repository.book.Book;

public class SearchResult implements Comparable<SearchResult> {

    private final Book book;

    private final int appearance;

    public SearchResult(Book book, int appearance) {
        this.book = book;
        this.appearance = appearance;
    }

    @Override
    public int compareTo(SearchResult other) {
        if (appearance < other.appearance) return 1;
        if (appearance > other.appearance) return -1;
        else {
            return book.title().compareTo(other.book.title());
        }
    }


}
