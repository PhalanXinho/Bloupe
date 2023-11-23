package indexer;

import datalakereader.FileReader;
import datamarthandler.Word;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Indexer {
    private final FileReader fileReader = new FileReader();

    public List<Word> invertedIndex(Path file) throws IOException {
        if (file.toString().endsWith(".txt")) {
            List<Word> wordList = new ArrayList<>();
            String bookId = fileReader.getBookId(file);
            Map<String, Integer> map = fileReader.wordTokenizer(file);
            for (Map.Entry<String, Integer> entry : map.entrySet())
                wordList.add(new Word(entry.getKey(), bookId, entry.getValue()));
            return wordList;
        }
        return null;
    }
}




