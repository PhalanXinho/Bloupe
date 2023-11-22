package datalake.downloader;

import com.hazelcast.collection.ISet;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Random;

public class RandomNumberGenerator {
    private final int maxNumber;
    public RandomNumberGenerator(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int generateRandomNumber(){
        int randInt = new Random().nextInt(maxNumber) + 1;
        if (verifyUnicity(randInt)){
            return new Random().nextInt(maxNumber) + 1;
        }else{
            generateRandomNumber();
        }
        return 0;
    }

    
    private boolean verifyUnicity(int randInt){
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        ISet<Integer> usedNumbers = hazelcastInstance.getSet("usedNumbers");
        if (usedNumbers.contains(randInt)){
            hazelcastInstance.shutdown();
            return false;
        } else {
            usedNumbers.add(randInt);
            hazelcastInstance.shutdown();
            return true;
        }
    }
}
