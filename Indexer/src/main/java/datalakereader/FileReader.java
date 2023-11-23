package datalakereader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileReader{

    public String getBookId(Path file) {
        String fileNameWithExtension = file.getFileName().toString();
        String regex = "book(\\d+)\\.txt";
        return fileNameWithExtension.replaceAll(regex, "$1");
    }

    private String getBookLanguage(Path file) {
        try {
            File f = new File(String.valueOf(file));
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                if (data.startsWith("Language: ")) {
                    return data.replace("Language: ", "");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Integer> wordTokenizer(Path file) throws IOException {
        String language = getBookLanguage(file);
        FilterMeaningfullWords meaningfullWords = new FilterMeaningfullWords();
        meaningfullWords.storeLanguage(language);
        Scanner scanner = skipMetadata(file);
        Map<String, Integer> words = new HashMap<>();
        scanner.useDelimiter("[^a-zA-Z]+");
        while (scanner.hasNext()) {
            String word = scanner.next();
            word = word.toLowerCase();
            addToDictionary(word, words, meaningfullWords);
        }
        scanner.close();
        return words;
    }

    private static Scanner skipMetadata(Path file) throws IOException {
        String fileContent = new String(Files.readAllBytes(file), "UTF-8");
        Scanner scanner = new Scanner(fileContent);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().startsWith("*** START OF THE PROJECT GUTENBERG")) {
                break;
            }
        }
        return scanner;
    }

    private void addToDictionary(String word, Map<String, Integer> words, FilterMeaningfullWords meaningfullWords) {
        if (meaningfullWords.isMeaningfulWord(word)) {
            if (words.containsKey(word)) {
                words.put(word, words.get(word) + 1);
            } else {
                words.put(word, 1);
            }
        }

    }
}
