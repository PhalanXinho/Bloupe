import bookmanager.FileContentManager;
import bookrepository.BookRepository;
import bookrepository.PostgreSQLBookRepository;
import broker.ArtemisMQBooksConsumer;
import broker.BooksConsumer;
import datalake.DataLakeManager;
import datalake.GoogleCloudDataLakeManager;
import datamart.DataMartManager;
import datamart.GoogleCloudDataMartManager;
import datamart.HazelcastDataMartManager;
import datamart.IndexedWordResult;
import domain.Book;
import indexer.Indexer;
import metadata.MetadataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Controller {

    private final Logger logger = LoggerFactory.getLogger(Controller.class);
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
            logger.info("Request to index book: " + filePath);


            logger.info("Downloading " + filePath + " from the data lake...");
            String fileContent = dataLakeManager.read(filePath);
            int bookId = dataLakeManager.getIdFromFileName(filePath);
            logger.info("Downloading finished successfully");

            FileContentManager fileContentManager = new FileContentManager(fileContent);

            String metadata = fileContentManager.getMetadataPart();
            String content = fileContentManager.getContentPart();

            MetadataManager metadataManager = new MetadataManager(metadata);
            Book book = metadataManager.bookFromMetadata(bookId);

            logger.info("Saving book with id=" + book.id() + " to the book database...");
            bookRepository.save(book);
            logger.info("Saving finished successfully");


            logger.info("Starting the indexing process...");
            List<IndexedWordResult> indexedWordResultList = indexer.invertedIndex(content, book);
            logger.info("Indexing process finished successfully");

            logger.info("Adding " + indexedWordResultList.size() + " results into the data mart");
            for ( int i = 0; i < indexedWordResultList.size(); i++) {
                if ( i % 250 == 0 )
                    logger.info(i + " out of " + indexedWordResultList.size() + " words added into data mart");
                dataMartManager.addWordToDataMart(indexedWordResultList.get(i));
            }
            logger.info("Added " + indexedWordResultList.size() + " results into the data mart");
        }
    }
}


