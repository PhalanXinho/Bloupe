package repository.book;

import java.sql.Date;

public record Book(int id,
                   String title,
                   String author,
                   Date releaseDate,
                   String language)
{}
