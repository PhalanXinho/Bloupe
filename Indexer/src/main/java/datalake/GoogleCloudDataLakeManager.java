package datalake;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class GoogleCloudDataLakeManager implements DataLakeManager {

    private final Bucket bucket;

    public GoogleCloudDataLakeManager() {
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
    public String read(String fileName) {
        return new String(bucket.get(fileName).getContent(), StandardCharsets.UTF_8);
    }

    @Override
    public int getIdFromFileName(String fileName) {
        int startIdx = fileName.lastIndexOf('/') + 1;
        int endIdx = fileName.indexOf(".txt");
        return Integer.parseInt(fileName.substring(startIdx, endIdx));
    }
}
