package br.edu.example.jonathan.themovie.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.edu.example.jonathan.themovie.R;
import br.edu.example.jonathan.themovie.TmdbConnectionException;
import br.edu.example.jonathan.themovie.TmdbMovie;

public final class TMDBUtil {

    public static final String API_BASE_URL = "https://api.themoviedb.org/3";

    public static final String CONFIGURATION_URL = API_BASE_URL + "/configuration";

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";

    public static final String DISCOVER_URL = API_BASE_URL + "/discover";

    public static final String DISCOVER_MOVIES_URL = DISCOVER_URL + "/movie";

    public static String queryTheMostPopularMovies(Context context) throws Exception {
        String apiKey = context.getString(R.string.tmdb_api_key);
        String sortBy = "popularity.desc";
        String includeAdult = String.valueOf(false);
        String includeVideo = String.valueOf(false);
        String pageCount = "1";
        Uri uri = Uri.parse(DISCOVER_MOVIES_URL).buildUpon()
                .appendQueryParameter("api_key", apiKey)
                .appendQueryParameter("sort_by", sortBy)
                .appendQueryParameter("include_adult", includeAdult)
                .appendQueryParameter("include_video", includeVideo)
                .appendQueryParameter("page", pageCount).build();
        HttpURLConnection httpConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(uri.toString());
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            if (httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                String message = context.getString(R.string.fail_to_connect_to_tmdb);
                throw new TmdbConnectionException(message, httpConnection.getResponseCode(),
                        httpConnection.getResponseMessage());
            }
            InputStream inputStream = httpConnection.getInputStream();
            StringBuilder json = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            return json.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
    }

    public static Bitmap queryPosterImage(Context context, String imagePath) throws IOException {
        String imageUrl = IMAGE_BASE_URL + "/w500" + imagePath;
        return Picasso.with(context).load(imageUrl).get();
    }

    @NonNull
    public static List<TmdbMovie> convertJsonToMovies(String json, int moviesMaxCount) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");
        int resultsCount = results.length();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        List<TmdbMovie> movies = new ArrayList<>();
        for (int i = 0; i < resultsCount; i++) {
            if (i == moviesMaxCount) {
                break;
            }
            JSONObject result = results.getJSONObject(i);
            JSONArray genreIdsArray = result.getJSONArray("genre_ids");
            long voteCount = result.getLong("vote_count");
            long id = result.getLong("id");
            boolean isAdult = result.getBoolean("adult");
            String posterPath = result.getString("poster_path");
            String overview = result.getString("overview");
            String releaseDateString = result.getString("release_date");
            Date releaseDate = dateFormat.parse(releaseDateString);
            String originalTitle = result.getString("original_title");
            String originalLanguage = result.getString("original_language");
            String title = result.getString("title");
            String backdropPath = result.getString("backdrop_path");
            double popularity = result.getDouble("popularity");
            boolean hasVideo = result.getBoolean("video");
            double voteAverage = result.getDouble("vote_average");
            List<Long> genreIds = new ArrayList<>();
            for (int j = 0; j < genreIdsArray.length(); j++) {
                long genreId = genreIdsArray.getLong(j);
                genreIds.add(genreId);
            }
            TmdbMovie movie = new TmdbMovie();
            movie.setId(id);
            movie.setVoteCount(voteCount);
            movie.setAdult(isAdult);
            movie.setPosterPath(posterPath);
            movie.setOverview(overview);
            movie.setReleaseDate(releaseDate);
            movie.setOriginalTitle(originalTitle);
            movie.setOriginalLanguage(originalLanguage);
            movie.setTitle(title);
            movie.setBackdropPath(backdropPath);
            movie.setPopularity(popularity);
            movie.setVideo(hasVideo);
            movie.setVoteAverage(voteAverage);
            movie.getGenreIds().addAll(genreIds);
            movies.add(movie);
        }
        return movies;
    }

}
