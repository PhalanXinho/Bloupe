package basedatabase;

import metadata.Metadata;
import org.bson.Document;

public class MetadataDatabase {
    private MongoDBConnection connection;
    private MetadataJsonConverter converter;

    public MetadataDatabase(MongoDBConnection connection, MetadataJsonConverter converter) {
        this.connection = connection;
        this.converter = converter;
    }

    public void insertMetadata(Metadata metadata) {
        String json = converter.metadataToJson(metadata);
        Document document = Document.parse(json);
        connection.getCollection().insertOne(document);
    }

    public void close() {
        connection.close();
    }

    public void drop() {
        connection.getCollection().drop();
    }
}