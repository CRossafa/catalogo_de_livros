package br.com.alura.catalogo_de_livros.services;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}