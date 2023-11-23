package basedatabase;

import com.google.gson.Gson;
import datamarthandler.Metadata;

public class MetadataJsonConverter {
    private Gson gson;

    public MetadataJsonConverter() {
        this.gson = new Gson();
    }

    public String metadataToJson(Metadata metadata) {
        return gson.toJson(metadata);
    }
}