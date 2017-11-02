package br.edu.example.jonathan.themovie;

import java.util.Locale;

public class TmdbConnectionException extends Exception {

    public TmdbConnectionException(String message, int responseCode, String responseMessage) {
        super(String.format(Locale.US, "%s: %d - %s", message, responseCode, responseMessage));
    }

}
