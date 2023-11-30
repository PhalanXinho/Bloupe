package datalakeuploader;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import domain.Book;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class GoogleCloudDataLakeBookUploader implements DataLakeBookUploader {

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
        String fileName = getFileNameFromId(book.id());

        if ( fileAlreadyExists(book.id()))
            throw new FileAlreadyUploadedException("File \"" + fileName + "\" already exists.");

        bucket.create(fileName, book.content().getBytes(StandardCharsets.UTF_8));
    }

    private String getFileNameFromId(int id) {
        int firstDigit = id / 100000;
        int secondDigit = id / 10000;
        int thirdDigit = id / 1000;
        return firstDigit + "/" + secondDigit + "/" + thirdDigit + "/" + id + ".txt";
    }

    private boolean fileAlreadyExists(int id) {
        String fileName = getFileNameFromId(id);
        Blob blob = bucket.get(fileName);
        return blob.exists();
    }
}
