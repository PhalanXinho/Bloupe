package datamarthandler;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.HashMap;
import java.util.Map;

public class HazelcastDatamart implements DatamartHandler {
    public IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> createDatamart() {
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
        return hzInstance.getMap("firstMap");

    }
    public void addWordToDatamart(Word word, IMap<Character, Map<Character, Map<String, Map<String, Integer>>>> map) {
        Character firstCharacter = word.word().charAt(0);
        Character secondCharacter = word.word().charAt(1);

        Map<Character, Map<String, Map<String, Integer>>> firstCharacterMap = map.get(firstCharacter);

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

        map.put(firstCharacter, firstCharacterMap);
    }
}
