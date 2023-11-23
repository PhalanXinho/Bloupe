package datalake;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePathProvider {
    private final String year = new SimpleDateFormat("yyyy").format(new Date());
    private final String month = new SimpleDateFormat("MM").format(new Date());
    private final String day = new SimpleDateFormat("dd").format(new Date());

    public Path provideDatePath() {
        return Paths.get(year, month, day);
    }
}
