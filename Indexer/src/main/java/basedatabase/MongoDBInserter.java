package basedatabase;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import metadata.Metadata;
import org.bson.Document;

public class MongoDBInserter {
    private final MongoDBConnection connection;
    private final MetadataJsonConverter converter;

    public MongoDBInserter(MongoDBConnection connection, MetadataJsonConverter converter) {
        this.connection = connection;
        this.converter = converter;
    }

    public void insertMetadata(Metadata metadata) {
        String json = converter.metadataToJson(metadata);
        Document document = Document.parse(json);
        document.put("_id", metadata.id());
        connection.getCollection().createIndex(Indexes.ascending("id"), new IndexOptions().unique(true));
        try {
            connection.getCollection().insertOne(document);
        } catch (MongoWriteException e) {
            if (ErrorCategory.fromErrorCode(e.getCode()) == ErrorCategory.DUPLICATE_KEY) {
                System.out.println("Id is in collection.");
            } else {
                throw e;
            }
        }
    }

    public MongoDBConnection getConnection() {
        return connection;
    }
}