package generator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IdGenerator implements IntGenerator {

    private Set<Integer> generatedIntegers = new HashSet<>();

    private final int LIMIT = 70000;

    @Override
    public Integer generate() {
        Random random = new Random();

        int number = random.nextInt(LIMIT);
        while ( generatedIntegers.contains(number) )
            number = random.nextInt();

        generatedIntegers.add(number);
        return number;
    }
}
