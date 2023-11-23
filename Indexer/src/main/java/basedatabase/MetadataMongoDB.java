package basedatabase;

import metadata.Metadata;

public class MetadataMongoDB implements MetadataDatabase {
    private MongoDatabase database;
    public void createMetadataDatabase() {
        MongoDBConnection connection = null;
        try {
            connection = new MongoDBConnection("localhost", 27017, "MetadataDatabase", "metadata");
            MetadataJsonConverter converter = new MetadataJsonConverter();
            database = new MongoDatabase(connection, converter);
        } catch (Exception e) {
            System.out.println("Failed to insert metadata to MongoDB: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void insertMetadata(Metadata bookMetadata) {
        database.insertMetadata(bookMetadata);
    }
}

