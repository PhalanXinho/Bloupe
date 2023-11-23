package datalake.reader;

import java.io.File;
import java.nio.file.Path;

public class Reader {

    public void read(Path path) {
        File[] files = new File(String.valueOf(path)).listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    read(Path.of(file.getAbsolutePath()));
                }
            }
        }
    }
}
