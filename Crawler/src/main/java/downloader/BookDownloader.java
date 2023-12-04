package downloader;

import domain.Book;

public interface BookDownloader extends Downloader<Book, Integer> {

    @Override
    Book download(Integer key) throws BookNotFoundException;
}
