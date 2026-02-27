package mogodbDemo.atlas;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MovieQueries {

    public long howManyMovies(List<Movie> movieList) {
        return safeList(movieList).stream()
                .filter(m -> m != null && m.getYear() == 1975)
                .count();
    }

    public int lengthOfLongestMovie(List<Movie> movieList) {
        return safeList(movieList).stream()
                .filter(Objects::nonNull)
                .mapToInt(Movie::getRuntime)
                .max()
                .orElse(0);
    }

    public long howManyUniqueGenres(List<Movie> movieList) {
        return safeList(movieList).stream()
                .filter(m -> m != null && m.getYear() == 1975)
                .flatMap(m -> safeList(m.getGenres()).stream())
                .filter(Objects::nonNull)
                .distinct()
                .count();
    }

    public List<String> actorsInHighestRatedMovie(List<Movie> movieList) {
        Optional<Movie> top = safeList(movieList).stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Movie::getImdbRating));

        return top.map(m -> safeList(m.getCast()).stream()
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList()))
                .orElseGet(List::of);
    }

    public String movieWithTheFewestActorsListed(List<Movie> movieList) {
        return safeList(movieList).stream()
                .filter(Objects::nonNull)
                .min(Comparator.comparingInt(m -> safeList(m.getCast()).size()))
                .map(Movie::getTitle)
                .orElse("");
    }

    public long amountOfActorsInMoreThanOneMovie(List<Movie> movieList) {
        Map<String, Long> counts = safeList(movieList).stream()
                .filter(Objects::nonNull)
                .flatMap(m -> safeList(m.getCast()).stream())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return counts.values().stream()
                .filter(c -> c != null && c > 1)
                .count();
    }

    public String actorInTheMostMovies(List<Movie> movieList) {
        return safeList(movieList).stream()
                .filter(Objects::nonNull)
                .flatMap(m -> safeList(m.getCast()).stream())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    public long allUniqueLanguages(List<Movie> movieList) {
        return safeList(movieList).stream()
                .filter(Objects::nonNull)
                .flatMap(m -> safeList(m.getLanguages()).stream())
                .filter(Objects::nonNull)
                .distinct()
                .count();
    }

    public boolean anyDoubletteTitles(List<Movie> movieList) {
        Map<String, Long> titleCounts = safeList(movieList).stream()
                .filter(Objects::nonNull)
                .map(Movie::getTitle)
                .filter(t -> t != null && !t.isBlank())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return titleCounts.values().stream().anyMatch(c -> c != null && c > 1);
    }

    private static <T> List<T> safeList(List<T> list) {
        return list == null ? List.of() : list;
    }
}