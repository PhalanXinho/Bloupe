package indexer;

import datalakereader.FileTokenizer;
import datamart.IndexedWordResult;
import domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Indexer {
    private final FileTokenizer fileTokenizer = new FileTokenizer();

    public List<IndexedWordResult> invertedIndex(String bookContent, Book book) {
        List<IndexedWordResult> indexedWordResultList = new ArrayList<>();
        Map<String, Integer> map = fileTokenizer.wordTokenizer(bookContent, book);
        for (Map.Entry<String, Integer> entry : map.entrySet())
            indexedWordResultList.add(new IndexedWordResult(entry.getKey(), book.id(), entry.getValue()));
        return indexedWordResultList;
    }
}




