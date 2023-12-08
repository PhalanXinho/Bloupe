package filter;

import search.SearchResult;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class StreamSearchResultFilter implements SearchResultFilter {

    private final String author;

    private final String from;

    private final String to;

    public StreamSearchResultFilter(String author, String from, String to) {
        this.author = author;
        this.from = from;
        this.to = to;
    }

    @Override
    public List<SearchResult> filter(List<SearchResult> results) {

        final Date fromDate = (this.from != null) ? new GregorianCalendar(Integer.parseInt(this.from), Calendar.JANUARY, 1).getTime() : null;
        final Date toDate = (this.to != null) ? new GregorianCalendar(Integer.parseInt(this.to), Calendar.DECEMBER, 31).getTime() : null;


        results = results.stream().filter(x -> {
            if (author == null) return true;
            return x.book().author().equals(this.author);
        }).filter(x -> {
            if (toDate == null) return true;
            return x.book().releaseDate().before(toDate);
        }).filter(x -> {
            if (fromDate == null) return true;
            return x.book().releaseDate().after(fromDate);
        }).toList();
        return results;
    }
}
