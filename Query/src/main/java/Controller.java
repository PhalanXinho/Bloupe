import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import filter.SearchResultFilter;
import filter.StreamSearchResultFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.book.BookRepository;
import repository.book.PostgreSQLBookRepository;
import repository.search.HazelcastSearchRepository;
import repository.search.SearchRepository;
import search.SearchResult;
import search.SearchService;
import search.SearchServiceImpl;
import spark.Spark;

import java.util.List;
public class Controller {

    private final Logger logger = LoggerFactory.getLogger(Main.class);

    private final BookRepository bookRepository = new PostgreSQLBookRepository();
    private final SearchRepository searchRepository = new HazelcastSearchRepository("datamart.json");
    private final SearchService searchService = new SearchServiceImpl(searchRepository, bookRepository);

    public void execute(){
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        Spark.get("/document/:word", "application/json", (request, response) -> {

            String from = request.queryParams("from");
            String to = request.queryParams("to");
            String author = request.queryParams("author");

            logger.info("Received request " + request.url() + " from " + request.ip());
            logger.info("Author: " + author );
            logger.info("From: " + from);
            logger.info("To: " + to);

            response.type("application/json");
            List<SearchResult> searchResult = searchService.search(request.params(":word"));

            SearchResultFilter filter = new StreamSearchResultFilter(author, from, to);
            searchResult = filter.filter(searchResult);

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd").create();
            return gson.toJson(searchResult);
        });
    }
}
