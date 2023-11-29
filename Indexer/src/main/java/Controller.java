import bookmanager.FileContentManager;
import bookrepository.BookRepository;
import bookrepository.PostgreSQLBookRepository;
import broker.ArtemisMQBooksConsumer;
import broker.BooksConsumer;
import datalake.DataLakeManager;
import datalake.GoogleCloudDataLakeManager;
import datamarthandler.DataMartManager;
import datamarthandler.HazelcastDataMartManager;
import datamarthandler.Word;
import domain.Book;
import indexer.Indexer;
import metadata.MetadataManager;

import java.util.List;

public class Controller {
    private final DataMartManager dataMartManager = new HazelcastDataMartManager();
    private final BookRepository bookRepository = new PostgreSQLBookRepository();
    private final DataLakeManager dataLakeManager = new GoogleCloudDataLakeManager();
    private final BooksConsumer booksConsumer = new ArtemisMQBooksConsumer();
    private final Indexer indexer = new Indexer();

    public void start() {

        /*
        1. consume message from queue
        2. download the file from datalake
        3. get file content
        4. get file metadata
        5. create Book object from metadata
        6. save Book object to database
        7. index file content
        9. add words to datamart
        8. wait for another message from queue
        */

        while (true) {
            String filePath = booksConsumer.consume();

            String fileContent = dataLakeManager.read(filePath);
            int bookId = dataLakeManager.getIdFromFileName(filePath);

            FileContentManager fileContentManager = new FileContentManager(fileContent);

            String metadata = fileContentManager.getMetadataPart();
            String content = fileContentManager.getContentPart();

            MetadataManager metadataManager = new MetadataManager(metadata);
            Book book = metadataManager.bookFromMetadata(bookId);
            bookRepository.save(book);

            List<Word> wordList = indexer.invertedIndex(content, book);
            for (Word word : wordList) {
                dataMartManager.addWordToDataMart(word);
            }
        }
    }
}


