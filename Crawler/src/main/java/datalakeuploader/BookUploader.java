package datalakeuploader;

import domain.Book;

public interface BookUploader extends Uploader<Book> {

    String getFileNameFromBook(Book book);
}
