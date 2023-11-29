package indexer;

import datalakereader.FileTokenizer;
import datamarthandler.Word;
import domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Indexer {
    private final FileTokenizer fileTokenizer = new FileTokenizer();

    public List<Word> invertedIndex(String bookContent, Book book) {
        List<Word> wordList = new ArrayList<>();
        Map<String, Integer> map = fileTokenizer.wordTokenizer(bookContent, book);
        for (Map.Entry<String, Integer> entry : map.entrySet())
            wordList.add(new Word(entry.getKey(), Integer.toString(book.id()), entry.getValue()));
        return wordList;
    }
}




