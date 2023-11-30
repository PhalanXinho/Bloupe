import broker.ArtemisMQProducer;
import broker.StringProducer;
import datalakeuploader.DataLakeBookUploader;
import datalakeuploader.FileAlreadyUploadedException;
import datalakeuploader.GoogleCloudDataLakeBookUploader;
import domain.Book;
import downloader.BookDownloader;
import downloader.BookNotFoundException;
import downloader.GutenbergProjectBookDownloader;
import generator.IdGenerator;
import generator.IntGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final DataLakeBookUploader dataLakeBookUploader = new GoogleCloudDataLakeBookUploader();

    private final IntGenerator generator = new IdGenerator();

    private final BookDownloader downloader = new GutenbergProjectBookDownloader();

    private final StringProducer producer = new ArtemisMQProducer();

    public boolean run() {

        logger.info("Starting...");

        int bookId = generator.generate();

        Book book;
        try {
            book = downloader.download(bookId);
        } catch (BookNotFoundException e) {
            logger.warn(e.getMessage());
            return false;
        }

        try {
            dataLakeBookUploader.upload(book);
        } catch (FileAlreadyUploadedException e) {
            logger.warn(e.getMessage());
            return false;
        }

        producer.produce(dataLakeBookUploader.getFileNameFromBook(book));
        logger.info("Controller successfully finished");
        return true;
    }
}
