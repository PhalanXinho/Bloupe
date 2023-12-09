package repository.book;

import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(int id);
}
