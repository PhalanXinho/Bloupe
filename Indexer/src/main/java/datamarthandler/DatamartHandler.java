package datamarthandler;

import com.hazelcast.map.IMap;

import java.util.Map;

public interface DatamartHandler {
    IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> createDatamart();
    void addWordToDatamart(Word word, IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> map);
}
