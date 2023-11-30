package datalakeuploader;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import domain.Book;
import downloader.GutenbergProjectBookDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class GoogleCloudDataLakeBookUploader implements DataLakeBookUploader {

    Logger logger = LoggerFactory.getLogger(GoogleCloudDataLakeBookUploader.class);

    private final Bucket bucket;

    public GoogleCloudDataLakeBookUploader() {
        Properties properties = new Properties();
        GoogleCredentials credentials;
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("storage.properties"));
            credentials = GoogleCredentials.getApplicationDefault();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        this.bucket = storage.get(properties.getProperty("DATA_LAKE_BUCKET_NAME"));
    }

    @Override
    public void upload(Book book) throws FileAlreadyUploadedException {

        logger.info("Uploading book with id=" + book.id() + " to the DataLake...");
        String fileName = getFileNameFromBook(book);

        if ( bookAlreadyExists(book))
            throw new FileAlreadyUploadedException("File \"" + fileName + "\" already exists.");

        bucket.create(fileName, book.content().getBytes(StandardCharsets.UTF_8));
        logger.info("Book with id=" + book.id() + " uploaded");
    }


    @Override
    public String getFileNameFromBook(Book book) {
        int id = book.id();
        int firstDigit = id / 100000;
        int secondDigit = id / 10000;
        int thirdDigit = id / 1000;
        return firstDigit + "/" + secondDigit + "/" + thirdDigit + "/" + id + ".txt";
    }

    private boolean bookAlreadyExists(Book book) {
        String fileName = getFileNameFromBook(book);
        return bucket.get(fileName) != null;
    }
}
