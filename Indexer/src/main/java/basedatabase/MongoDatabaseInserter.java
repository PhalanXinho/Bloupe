package basedatabase;

import metadata.Metadata;
import org.bson.Document;

public class MongoDatabaseInserter {
    private final MongoDBConnection connection;
    private final MetadataJsonConverter converter;

    public MongoDatabaseInserter(MongoDBConnection connection, MetadataJsonConverter converter) {
        this.connection = connection;
        this.converter = converter;
    }

    public void insertMetadata(Metadata metadata) {
        String json = converter.metadataToJson(metadata);
        Document document = Document.parse(json);
        connection.getCollection().insertOne(document);
    }

    public MongoDBConnection getConnection() {
        return connection;
    }
}