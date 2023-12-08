package datamart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import es.ulpgc.bigdata.SharedDataMartEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HazelcastDataMartManager implements DataMartManager {

    private final IMap<Character, Map<Character, Map<String, List<SharedDataMartEntry>>>> dataMart;

    public HazelcastDataMartManager() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        this.dataMart = instance.getMap("dataMart");
    }

    @Override
    public boolean saveIntoFile(String path) {
        try {
            String json = new ObjectMapper().writeValueAsString(this.dataMart);
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void addWordToDataMart(IndexedWordResult indexedWordResult) {

        SharedDataMartEntry dataMartEntry = new SharedDataMartEntry( indexedWordResult.bookId(), indexedWordResult.frequency() );

        String word = indexedWordResult.word();

        Character firstCharacter = indexedWordResult.word().charAt(0);
        Character secondCharacter = indexedWordResult.word().charAt(1);

        Map<Character, Map<String, List<SharedDataMartEntry>>> firstCharacterMap = dataMart.get(firstCharacter);
        if ( firstCharacterMap == null ) {
            firstCharacterMap = new HashMap<>();
            dataMart.put(firstCharacter, firstCharacterMap);
        }

        Map<String, List<SharedDataMartEntry>> secondCharacterMap = firstCharacterMap.get(secondCharacter);
        if ( secondCharacterMap == null ) {
            secondCharacterMap = new HashMap<>();
            firstCharacterMap.put(secondCharacter, secondCharacterMap);
        }

        List<SharedDataMartEntry> savedBooks = secondCharacterMap.get(word);
        if ( savedBooks == null ) {
            savedBooks = new ArrayList<>();
            secondCharacterMap.put(word, savedBooks);
        }

        savedBooks.add(dataMartEntry);
        secondCharacterMap.put(word, savedBooks);
        firstCharacterMap.put(secondCharacter, secondCharacterMap);
        dataMart.put(firstCharacter, firstCharacterMap);
    }
}
