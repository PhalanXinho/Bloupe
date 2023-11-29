package datamarthandler;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.HashMap;
import java.util.Map;

public class HazelcastDataMartManager implements DataMartManager {

    private final IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> dataMart;

    public HazelcastDataMartManager() {
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
        this.dataMart = hzInstance.getMap("firstMap");
    }

    public void addWordToDataMart(Word word) {
        Character firstCharacter = word.word().charAt(0);
        Character secondCharacter = word.word().charAt(1);

        Map<Character, Map<String, Map<String, Integer>>> firstCharacterMap = dataMart.get(firstCharacter);

        if (firstCharacterMap == null) {
            firstCharacterMap = new HashMap<>();
        }

        Map<String, Map<String, Integer>> secondCharacterMap = firstCharacterMap.get(secondCharacter);

        if (secondCharacterMap == null) {
            secondCharacterMap = new HashMap<>();
        }

        Map<String, Integer> wordInfo = new HashMap<>();
        wordInfo.put(word.id(), word.frequency());
        secondCharacterMap.put(word.word(), wordInfo);
        firstCharacterMap.put(secondCharacter, secondCharacterMap);

        dataMart.put(firstCharacter, firstCharacterMap);
    }
}
