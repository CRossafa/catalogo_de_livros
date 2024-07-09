package br.com.alura.catalogo_de_livros.model;

import java.util.Arrays;

public enum Idiomas {
    PORTUGUESE("pt", "Português"),
    ENGLISH("en", "Inglês"),
    SPANISH("es", "Espanhol"),
    FRENCH("fr", "Francês");

    private final String idiomasAPI;
    private final String idiomaPortugues;

    Idiomas(String idiomasAPI, String idiomaPortugues) {
        this.idiomasAPI = idiomasAPI;
        this.idiomaPortugues = idiomaPortugues;
    }

    public String getIdiomasAPI() {
        return idiomasAPI;
    }

    public String getIdiomaPortugues() {
        return idiomaPortugues;
    }

    public static Idiomas fromString(String text) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.idiomasAPI.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para esse código: " + text);
    }

    public static Idiomas fromPortugues(String text) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.idiomaPortugues.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para esse idioma: " + text);
    }

    public static String toPortugues(Idiomas idioma) {
        return idioma.getIdiomaPortugues();
    }

    public static Idiomas[] getValues() {
        return Arrays.stream(Idiomas.values()).toArray(Idiomas[]::new);
    }
}
