package basedatabase;

import com.google.gson.Gson;
import metadata.Metadata;

public class MetadataJsonConverter {
    private final Gson gson;

    public MetadataJsonConverter() {
        this.gson = new Gson();
    }

    public String metadataToJson(Metadata metadata) {
        return gson.toJson(metadata);
    }
}