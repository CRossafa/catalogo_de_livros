package br.com.alura.catalogo_de_livros.services;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter dados JSON", e);
        }
    }
}
