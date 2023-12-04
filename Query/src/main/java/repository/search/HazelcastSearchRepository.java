package repository.search;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import es.ulpgc.bigdata.SharedDataMartEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HazelcastSearchRepository implements SearchRepository {

    private final IMap<Character, Map<Character, Map<String, List<SharedDataMartEntry>>>> dataMart;

    public HazelcastSearchRepository() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        this.dataMart = instance.getMap("dataMart");
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
}
