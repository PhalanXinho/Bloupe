package basedatabase;

import metadata.Metadata;

public interface MetadataDatabase {
    void createMetadataDatabase();
    void insertMetadata(Metadata bookMetadata);

}
