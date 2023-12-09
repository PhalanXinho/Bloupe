package repository.search;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import es.ulpgc.bigdata.SharedDataMartEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HazelcastSearchRepository implements SearchRepository {

    private final IMap<Character, Map<Character, Map<String, List<SharedDataMartEntry>>>> dataMart;
    private final Logger logger = LoggerFactory.getLogger(HazelcastSearchRepository.class);


    public HazelcastSearchRepository(String file) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        this.dataMart = instance.getMap("dataMart");
        loadDataMartFromFile(file);
    }

    @Override
    public List<SharedDataMartEntry> getSearchResult(String word) {
        char firstChar = word.charAt(0);
        char secondChar = word.charAt(1);
        if (dataMart.get(firstChar) == null
                || dataMart.get(firstChar).get(secondChar) == null
                || dataMart.get(firstChar).get(secondChar).get(word) == null)
            return new ArrayList<>();
        return ((dataMart.get(firstChar)).get(secondChar)).get(word);
    }


    private void loadDataMartFromFile(String path) {
        if ( this.dataMart.isEmpty() ) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.dataMart.putAll(mapper.readValue(new File(path), new TypeReference<>() {
                }));
                logger.info("Successfully loaded datamart from " + path + " file");
            } catch (IOException e) {
                logger.info("Datamart file not found, using empty datamart");
            }
        }
    }
}
