package search.parser;

import java.util.List;

public class WordParser {
    public List<String> parse(String word) {
        return List.of(word.split("\\+"));
    }
}
