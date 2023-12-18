package datamart;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class GoogleCloudDataMartManager implements DataMartManager {

    Logger logger = LoggerFactory.getLogger(GoogleCloudDataMartManager.class);
    private final Bucket bucket;

    public GoogleCloudDataMartManager() {
        Properties properties = new Properties();
        GoogleCredentials credentials;
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("storage.properties"));
            credentials = GoogleCredentials.getApplicationDefault();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        this.bucket = storage.get(properties.getProperty("DATA_MART_BUCKET_NAME"));
    }


    @Override
    public void addWordToDataMart(IndexedWordResult indexedWordResult) {
        String filePath = getFilePathFromWord(indexedWordResult.word());

        String appendedLine = indexedWordResult.bookId() + ";" + indexedWordResult.frequency() + "\n";

        Blob blob = bucket.get(filePath);
        if (blob == null) {
            bucket.create(filePath, appendedLine.getBytes(StandardCharsets.UTF_8));
        }
        else
            appendToBlob(blob, filePath, appendedLine);
    }

    @Override
    public boolean saveIntoFile() {
        logger.info("GoogleCloudDataMart cant be saved into file.");
        return false;
    }

    private String getFilePathFromWord( String word ) {
        return word.charAt(0) + "/" + word.charAt(1) + "/" + word + ".txt";
    }

    private void appendToBlob(Blob blob, String fileName, String line) {
        String content = new String( blob.getContent(), StandardCharsets.UTF_8 );
        content = content + line;
        blob.delete();
        bucket.create(fileName, content.getBytes(StandardCharsets.UTF_8));
    }
}
