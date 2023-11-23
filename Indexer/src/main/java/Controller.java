import basedatabase.MetadataDatabase;
import basedatabase.MetadataJsonConverter;
import basedatabase.MongoDBConnection;
import com.hazelcast.map.IMap;
import datamarthandler.*;
import indexer.Indexer;
import metadata.Metadata;
import metadata.MetadataBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Controller implements Datamart {
    private IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> map;
    private final HazelcastDatamart hzdataMart = new HazelcastDatamart();
    private MetadataBuilder metadataBuilder = new MetadataBuilder();
    private final Indexer indexer = new Indexer();

    public void execute(String bookPath) throws InterruptedException, IOException {
        map = createDatamart();
        String id = runIndexer(bookPath, map);
        Metadata bookMetadata = metadataBuilder.buildMetadata(Path.of(bookPath), id);
        insertMetadataToMongoDB(bookMetadata);

        System.out.println(map.entrySet());
        System.out.println("The Indexing has been done.");
    }

    private String runIndexer(String bookPath, IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> map) throws InterruptedException {
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

    private void insertMetadataToMongoDB(Metadata bookMetadata) {
        MongoDBConnection connection = null;
        try {
            connection = new MongoDBConnection("localhost", 27017, "MetadataDatabase", "metadata");
            MetadataJsonConverter converter = new MetadataJsonConverter();
            MetadataDatabase database = new MetadataDatabase(connection, converter);
            database.insertMetadata(bookMetadata);
        } catch (Exception e) {
            System.out.println("Failed to insert metadata to MongoDB: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
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
