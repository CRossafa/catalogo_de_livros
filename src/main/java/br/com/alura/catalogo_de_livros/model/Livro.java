package br.com.alura.catalogo_de_livros.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Autor autor; // Mapeamento muitos para um

    @Enumerated(EnumType.STRING)
    private Idiomas idiomas;

    private Double numeroDownloads;

    public Livro() {
    }

    public Livro(DadosLivro dados, Autor autor) {
        this.titulo = dados.titulo();
        this.autor = autor;
        this.idiomas = Idiomas.fromString(dados.idiomas().get(0));
        this.numeroDownloads = dados.numeroDownloads();
    }

    public Livro(String titulo, List<String> idiomas, Double numeroDownloads, List<DadosAutor> autores) {
        this.titulo = titulo;
        this.idiomas = Idiomas.fromString(idiomas.get(0));
        this.numeroDownloads = numeroDownloads;
        Autor autor = new Autor(autores.get(0)
                .nome(), autores.get(0)
                .anoDeNascimento(), autores.get(0)
                .anoDeFalecimento());
        this.autor = autor;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idiomas getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idiomas idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "titulo='" + titulo + '\'' +
                ", autores=" + autor +
                ", idioma='" + idiomas + '\'' +
                ", numeroDownloads=" + numeroDownloads;
    }
}
