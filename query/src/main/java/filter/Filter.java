package filter;

import java.util.List;

public interface Filter<K> {

    List<K> filter (List<K> list );
}
