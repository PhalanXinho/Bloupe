package repository.search;

import es.ulpgc.bigdata.SharedDataMartEntry;

import java.util.List;

public interface SearchRepository {
    List<SharedDataMartEntry> getSearchResult(String word);
}
