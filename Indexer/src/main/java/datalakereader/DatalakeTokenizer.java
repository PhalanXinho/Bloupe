package datalakereader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface DatalakeTokenizer {
    Map<String, Integer> wordTokenizer(Path file) throws IOException;
}
