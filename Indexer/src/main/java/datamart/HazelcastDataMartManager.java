package datamart;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HazelcastDataMartManager implements DataMartManager {

    private final IMap<Character, Map<Character, Map<String, List<DataMartEntry>>>> dataMart;

    public HazelcastDataMartManager() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        this.dataMart = instance.getMap("dataMart");
    }

    @Override
    public void addWordToDataMart(IndexedWordResult indexedWordResult) {
        Character firstCharacter = indexedWordResult.word().charAt(0);
        Character secondCharacter = indexedWordResult.word().charAt(1);

        Map<Character, Map<String, List<DataMartEntry>>> firstCharacterMap = dataMart.get(firstCharacter);

        if (firstCharacterMap == null) {
            firstCharacterMap = new HashMap<>();
        }

        Map<String, List<DataMartEntry>> secondCharacterMap = firstCharacterMap.get(secondCharacter);

        if (secondCharacterMap == null) {
            secondCharacterMap = new HashMap<>();
        }

        List<DataMartEntry> wordInfo = new ArrayList<>();
        wordInfo.add(new DataMartEntry(indexedWordResult.bookId(), indexedWordResult.frequency()));

        secondCharacterMap.put(indexedWordResult.word(), wordInfo);
        firstCharacterMap.put(secondCharacter, secondCharacterMap);

        dataMart.put(firstCharacter, firstCharacterMap);
    }


    @Override
    public void findWord(String word) {
        try {
            List<DataMartEntry> results = dataMart.get(word.charAt(0)).get(word.charAt(1)).get(word);
            for (DataMartEntry dataMartEntry : results) {
                System.out.println(dataMartEntry.bookId() + "->" + dataMartEntry.appearance() + "x\n");
            }
        } catch (NullPointerException exception) {
            System.out.println("Word not found");
        }
    }
}
