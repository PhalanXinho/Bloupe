package search;

import java.util.List;

public interface SearchService {

    List<SearchResult> search(List<String> word);

}
