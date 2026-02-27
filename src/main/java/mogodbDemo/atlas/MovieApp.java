package mogodbDemo.atlas;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public class MovieApp {

    private final MovieQueries sm = new MovieQueries();

    public MovieApp() {

        Dotenv dotenv = Dotenv.load();
        String uri = dotenv.get("MONGODB_URI");

        if (uri == null || uri.isBlank()) {
            throw new IllegalStateException("MONGODB_URI saknas i .env");
        }

        List<Movie> movieList;
        try {
            MovieRepository repo = new MovieRepository(uri);
            movieList = repo.fetchMoviesByYear(1975);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println("1) Amount of movies (1975): " + sm.howManyMovies(movieList));
        System.out.println("2) Length of longest movie (runtime): " + sm.lengthOfLongestMovie(movieList));
        System.out.println("3) Amount of unique genres (1975): " + sm.howManyUniqueGenres(movieList));
        System.out.println("4) Actors in highest rated movie (imdb): " + sm.actorsInHighestRatedMovie(movieList));
        System.out.println("5) Movie with fewest actors (title): " + sm.movieWithTheFewestActorsListed(movieList));
        System.out.println("6) Amount of actors in more than one movie: " + sm.amountOfActorsInMoreThanOneMovie(movieList));
        System.out.println("7) Actor in the most movies: " + sm.actorInTheMostMovies(movieList));
        System.out.println("8) Amount of unique languages: " + sm.allUniqueLanguages(movieList));
        System.out.println("9) Has duplicate titles: " + sm.anyDoubletteTitles(movieList));
    }

    public static void main(String[] args) {
        new MovieApp();
    }
}