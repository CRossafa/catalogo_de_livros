package br.com.alura.catalogo_de_livros.services;

import br.com.alura.catalogo_de_livros.model.DadosLivro;
import br.com.alura.catalogo_de_livros.model.Autor;
import br.com.alura.catalogo_de_livros.model.Idiomas;
import br.com.alura.catalogo_de_livros.model.Livro;
import br.com.alura.catalogo_de_livros.repository.AutorRepository;
import br.com.alura.catalogo_de_livros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public List<Livro> salvarLivros(List<DadosLivro> dadosLivros) {
        List<Livro> livros = dadosLivros.stream().map(dadosLivro -> {
            Autor autor = dadosLivro.autores().stream()
                    .map(dadosAutor -> autorRepository.findByNome(dadosAutor.nome())
                            .orElseGet(() -> new Autor(dadosAutor.nome(), dadosAutor.anoDeNascimento(), dadosAutor.anoDeFalecimento())))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
            Livro livro = new Livro(dadosLivro, autor);
            autorRepository.save(autor);
            return livro;
        }).collect(Collectors.toList());

        return livroRepository.saveAll(livros);
    }

    public List<Livro> obterLivros() {
        return livroRepository.findAll();
    }

    public Optional<Livro> obterLivroPorId(Long id) {
        return livroRepository.findById(id);
    }

    public Optional<Livro> buscarLivroPorNome(String nomeLivro) {
        return livroRepository.findByTituloContainingIgnoreCase(nomeLivro);
    }

    public List<Autor> buscarAutoresPorTrechoDoNome(String nomeAutor) {
        return autorRepository.autorPorTrechoDoNome(nomeAutor);
    }

    public List<Livro> buscarLivroPorIdioma(String idioma) {
        return livroRepository.findByIdiomas(Idiomas.fromPortugues(idioma));
    }
}