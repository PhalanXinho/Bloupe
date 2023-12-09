package datalakereader;

import datalakereader.filterstopwords.FilterMeaningfulWords;
import domain.Book;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileTokenizer {

    public Map<String, Integer> wordTokenizer(String content, Book book) {

        FilterMeaningfulWords meaningfulWords = new FilterMeaningfulWords(book.language());

        Map<String, Integer> words = new HashMap<>();

        Scanner scanner = new Scanner(content);
        scanner.useDelimiter("[^a-zA-Z]+");

        while (scanner.hasNext()) {
            String word = scanner.next();
            word = word.toLowerCase();
            addToDictionary(word, words, meaningfulWords);
        }

        scanner.close();
        return words;
    }

    private void addToDictionary(String word, Map<String, Integer> words, FilterMeaningfulWords meaningfulWords) {
        if (meaningfulWords.isMeaningfulWord(word)) {
            if (words.containsKey(word)) {
                words.put(word, words.get(word) + 1);
            } else {
                words.put(word, 1);
            }
        }
    }
}
