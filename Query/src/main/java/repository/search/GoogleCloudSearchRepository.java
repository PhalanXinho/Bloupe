package repository.search;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import es.ulpgc.bigdata.SharedDataMartEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GoogleCloudSearchRepository implements SearchRepository {
    private final Bucket bucket;

    public GoogleCloudSearchRepository() {
        Properties properties = new Properties();
        GoogleCredentials credentials;
        try {
            properties.load( getClass().getClassLoader().getResourceAsStream("storage.properties") );
            credentials = GoogleCredentials.getApplicationDefault();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        this.bucket = storage.get(properties.getProperty("BUCKET_NAME"));
    }

    @Override
    public List<SharedDataMartEntry> getSearchResult(String word) {
        Blob blob = getFile(word);
        return getResultsFromFile(blob);
    }

    private String getFileNameStructure(String word) {
        return word.charAt(0) + "/" + word.charAt(1) + "/" + word + ".txt";
    }

    private List<SharedDataMartEntry> getResultsFromFile(Blob blob) {

        if (!blob.exists()) return new ArrayList<>();

        String content = new String(blob.getContent(), StandardCharsets.UTF_8);

        String[] lines = content.split("\\n");

        List<SharedDataMartEntry> entries = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(";");
            int bookId = Integer.parseInt(parts[0]);
            int appearance = Integer.parseInt(parts[1]);
            entries.add(new SharedDataMartEntry( bookId, appearance));
        }
        return entries;
    }

    private Blob getFile(String word) {
        String file = getFileNameStructure(word);
        return bucket.get(file);
    }

}
