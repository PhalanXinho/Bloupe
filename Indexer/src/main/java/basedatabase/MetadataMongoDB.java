package basedatabase;

import metadata.Metadata;

public class MetadataMongoDB implements MetadataDatabase {
    private MongoDBInserter database;

    public void createMetadataDatabase() {
        MongoDBConnection connection;
        try {
            connection = new MongoDBConnection("localhost", 27017, "MetadataDatabase", "metadata");
            MetadataJsonConverter converter = new MetadataJsonConverter();
            database = new MongoDBInserter(connection, converter);
        } catch (Exception e) {
            System.out.println("Failed to insert metadata to MongoDB: " + e.getMessage());
        }
    }


    public void insertMetadata(Metadata bookMetadata) {
        database.insertMetadata(bookMetadata);
        closeConnection(database.getConnection());
    }

    private void closeConnection(MongoDBConnection connection) {
        if (connection != null) {
            connection.close();
        }
    }
}

