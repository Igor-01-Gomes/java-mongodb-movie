package mogodbDemo.atlas;

import org.bson.Document;

import java.util.List;

public class Movie {

    private final String title;
    private final int year;
    private final int runtime;
    private final List<String> genres;
    private final List<String> cast;
    private final List<String> languages;
    private final double imdbRating;

    public Movie(String title,
                 int year,
                 int runtime,
                 List<String> genres,
                 List<String> cast,
                 List<String> languages,
                 double imdbRating) {
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.genres = genres;
        this.cast = cast;
        this.languages = languages;
        this.imdbRating = imdbRating;
    }

    public static Movie fromDocument(Document doc) {
        String title = doc.getString("title");

        Integer yearObj = doc.getInteger("year");
        int year = yearObj == null ? 0 : yearObj;

        Integer runtimeObj = doc.getInteger("runtime");
        int runtime = runtimeObj == null ? 0 : runtimeObj;

        List<String> genres = (List<String>) doc.get("genres");

        List<String> cast = (List<String>) doc.get("cast");

        List<String> languages = (List<String>) doc.get("languages");

        double imdbRating = 0.0;
        Document imdb = doc.get("imdb", Document.class);
        if (imdb != null) {
            Object r = imdb.get("rating");
            if (r instanceof Number n) {
                imdbRating = n.doubleValue();
            }
        }

        return new Movie(title, year, runtime, genres, cast, languages, imdbRating);
    }

    public String getTitle() { return title; }
    public int getYear() { return year; }
    public int getRuntime() { return runtime; }
    public List<String> getGenres() { return genres; }
    public List<String> getCast() { return cast; }
    public List<String> getLanguages() { return languages; }
    public double getImdbRating() { return imdbRating; }
}