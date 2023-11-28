import com.hazelcast.map.IMap;
import datamarthandler.*;
import domain.Book;
import indexer.Indexer;
import metadata.Metadata;
import metadata.MetadataBuilder;
import repository.book.BookRepository;
import repository.book.PostgreSQLBookRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Controller implements DatamartHandler {
    private final HazelcastDatamart hzdataMart = new HazelcastDatamart();
    private final BookRepository bookRepository = new PostgreSQLBookRepository();
    private final MetadataBuilder metadataBuilder = new MetadataBuilder();
    private final Indexer indexer = new Indexer();

    public void execute(String bookPath) throws IOException {

        IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> map = createDatamart();
        String id = runIndexer(bookPath, map);


        Book book = metadataBuilder.buildMetadata(Path.of(bookPath), id);
        bookRepository.save( book );


        System.out.println(map.entrySet());
        System.out.println("The Indexing has been done.");
    }

    private String runIndexer(String bookPath, IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> map) {
        try {
            List<Word> wordList = indexer.invertedIndex(Path.of(bookPath));
            String bookId = wordList.get(0).id();
            for (Word word : wordList) {
                addWordToDatamart(word, map);
            }
            return bookId;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> createDatamart() {
        return hzdataMart.createDatamart();

    }

    @Override
    public void addWordToDatamart(Word word, IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> map) {
        hzdataMart.addWordToDatamart(word, map);
    }
}


