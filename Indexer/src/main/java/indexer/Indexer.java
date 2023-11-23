package indexer;

import datalakereader.DatalakeTokenizer;
import datalakereader.FileTokenizer;
import datamarthandler.Word;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Indexer implements DatalakeTokenizer {
    private final FileTokenizer fileTokenizer = new FileTokenizer();

    public List<Word> invertedIndex(Path file) throws IOException {
        if (file.toString().endsWith(".txt")) {
            List<Word> wordList = new ArrayList<>();
            String bookId = getBookId(file);
            Map<String, Integer> map = wordTokenizer(file);
            for (Map.Entry<String, Integer> entry : map.entrySet())
                wordList.add(new Word(entry.getKey(), bookId, entry.getValue()));
            return wordList;
        }
        return null;
    }

    public String getBookId(Path file) {
        String fileNameWithExtension = file.getFileName().toString();
        String regex = "book(\\d+)\\.txt";
        return fileNameWithExtension.replaceAll(regex, "$1");
    }

    @Override
    public Map<String, Integer> wordTokenizer(Path file) throws IOException {
        return fileTokenizer.wordTokenizer(file);

    }

}




