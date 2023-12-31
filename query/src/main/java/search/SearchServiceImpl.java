package search;

import es.ulpgc.bigdata.SharedDataMartEntry;
import repository.search.SearchRepository;
import repository.book.Book;
import repository.book.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final BookRepository bookRepository;


    public SearchServiceImpl(SearchRepository searchRepository, BookRepository bookRepository) {
        this.searchRepository = searchRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<SearchResult> search(List<String> words) {

        List<SearchResult> result = new ArrayList<>();
        List<SharedDataMartEntry> indexEntries = new ArrayList<>();

        for (String word : words)
               indexEntries.addAll(searchRepository.getSearchResult(word.toLowerCase()));

        for (SharedDataMartEntry entry : indexEntries) {
            Optional<Book> book = bookRepository.findById(entry.bookId());
            if (book.isPresent()) {
                SearchResult searchResult = new SearchResult(book.get(), entry.appearance());
                result.add(searchResult);
            }
        }

        result.sort(null);
        return result;
    }
}
