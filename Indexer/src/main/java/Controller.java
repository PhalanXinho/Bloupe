import bookmanager.FileContentManager;
import bookrepository.BookRepository;
import bookrepository.PostgreSQLBookRepository;
import broker.ArtemisMQBooksConsumer;
import broker.BooksConsumer;
import datalake.DataLakeManager;
import datalake.GoogleCloudDataLakeManager;
import datamart.DataMartManager;
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
            for (int i = 0; i < indexedWordResultList.size(); i++) {
                if (i % 250 == 0)
                    logger.info(i + " out of " + indexedWordResultList.size() + " words added into data mart");
                dataMartManager.addWordToDataMart(indexedWordResultList.get(i));
            }
            logger.info("Added " + indexedWordResultList.size() + " results into the data mart");


            if ( dataMartManager.saveIntoFile("datamart.json") ) {
                logger.info("Data mart saved into datamart.json file");
            }
            else {
                logger.error("There was an error saving data mart into datamart.json file");
            }

        }
    }
}


