package datamart;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import es.ulpgc.bigdata.SharedDataMartEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HazelcastDataMartManager implements DataMartManager {

    private final String filePath;

    Logger logger = LoggerFactory.getLogger(HazelcastDataMartManager.class);
    private final IMap<Character, Map<Character, Map<String, List<SharedDataMartEntry>>>> dataMart;

    public HazelcastDataMartManager(String path) {
        this.filePath = path;
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        this.dataMart = instance.getMap("dataMart");

        loadDataMartFromFile();
    }

    private void loadDataMartFromFile() {
        if ( this.dataMart.isEmpty() ) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.dataMart.putAll(mapper.readValue(new File(this.filePath), new TypeReference<>() {
                }));
                logger.info("Successfully loaded datamart from " + filePath + " file");
            } catch (IOException e) {
                logger.info("Datamart file not found, using empty datamart");
            }
        }
    }


    @Override
    public boolean saveIntoFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(this.dataMart);
            FileWriter fileWriter = new FileWriter(this.filePath);
            fileWriter.write(json);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage());
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
