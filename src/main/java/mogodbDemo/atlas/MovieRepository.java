package mogodbDemo.atlas;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private final String uri;

    public MovieRepository(String uri) {
        if (uri == null || uri.isBlank()) {
            throw new IllegalArgumentException("MongoDB URI is missing");
        }
        this.uri = uri;
    }

    public List<Movie> fetchMoviesByYear(int year) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> moviesCollection = database.getCollection("movies");

            return moviesCollection
                    .find(new Document("year", year))
                    .map(Movie::fromDocument)
                    .into(new ArrayList<>());
        }
    }
}