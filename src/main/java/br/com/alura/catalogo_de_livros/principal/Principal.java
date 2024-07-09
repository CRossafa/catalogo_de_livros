package br.com.alura.catalogo_de_livros.principal;

import br.com.alura.catalogo_de_livros.model.Autor;
import br.com.alura.catalogo_de_livros.model.Dados;
import br.com.alura.catalogo_de_livros.model.Idiomas;
import br.com.alura.catalogo_de_livros.model.Livro;
import br.com.alura.catalogo_de_livros.repository.AutorRepository;
import br.com.alura.catalogo_de_livros.repository.LivroRepository;
import br.com.alura.catalogo_de_livros.services.ConverteDados;
import br.com.alura.catalogo_de_livros.services.ConsumoAPI;

import java.util.*;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private static final String BASE_URL = "https://gutendex.com/books/?search=";
    private LivroRepository livroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            String menu = """
                    
                    ****** Bem vindo(a) ao Catálogo de Livros - LiterAlura ******
                    
                    Aqui você poderá pesquisar por diversos títulos
                    da literatura nacional e internacional.
                    
                    Escolha uma opção do Menu...
                    
                    1 - Buscar livro na web
                    2 - Lista histórico de livros armazenados
                    3 - Listar histórico de autores armazenados
                    4 - Listar autores vivos por ano
                    5 - Listar livros por idioma
                    6 - Listar os Top 5 livros mais baixados
                    7 - Listar autor pelo nome
                    
                    0- Sair
                    
                    - Opção:
                   
                    """;

            System.out.println("\n" + menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1 -> obterDadosLivro();
                case 2 -> listarHistoricoDeLivros();
                case 3 -> listarHistoricoDeAutores();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLivrosPorIdioma();
                case 6 -> listarTop5LivrosBaixados();
                case 7 -> listarAutorPeloNome();
                case 0 -> System.out.println("\nPrograma encerrado com sucesso");
                default -> System.out.println("\nOpção inválida, tente novamente!");
            }
        }
    }

    private String solicitarDados() {
        System.out.println("Digite o nome do livro para a busca");
        return leitura.nextLine();
    }

    private Dados buscarDadosAPI(String nomeDoLivro) {
        var json = consumoApi.obterDados(BASE_URL + nomeDoLivro.replace(" ", "+"));
        var dados = conversor.obterDados(json, Dados.class);
        System.out.println("\n" + dados);
        return dados;
    }

    private Optional<Livro> obterInfoLivro(Dados dadosLivro, String nomeLivro) {
        return dadosLivro.results().stream()
                .filter(l -> l.titulo().toLowerCase().contains(nomeLivro.toLowerCase()))
                .map(b -> new Livro(b.titulo(), b.idiomas(), b.numeroDownloads(), b.autores()))
                .findFirst();
    }

    private void obterDadosLivro() {
        String tituloLivro = solicitarDados();
        Dados infoLivro = buscarDadosAPI(tituloLivro);
        Optional<Livro> livro = obterInfoLivro(infoLivro, tituloLivro);

        if (livro.isPresent()) {
            Livro l = livro.get();
            Autor autor = l.getAutor();

            if (autor != null) {
                Optional<Autor> autorExistente = autorRepositorio.findByNome(autor.getNome());

                if (autorExistente.isEmpty()) {
                    autorRepositorio.save(autor);
                } else {
                    l.setAutor(autorExistente.get());
                }

                livroRepositorio.save(l);
                System.out.println(l);
                System.out.println("\nLivro registrado com sucesso!");
            } else {
                System.out.println("\nO livro encontrado não possui um autor associado!");
            }
        } else {
            System.out.println("\nLivro não encontrado!");
        }
    }

    public void exibeDadosLivros(Livro livro) {
        String idiomaFormatado = Idiomas.toPortugues(livro.getIdiomas());

        var exibeLivro = """
            \n******* LIVRO *******
            \nTitulo: %s
            \nAutor: %s
            \nIdioma: %s
            \nNumero de downloads: %.0f
            \n***********************************\n
            """.formatted(livro.getTitulo(), livro.getAutor() != null ? livro.getAutor().getNome() : "Desconhecido", idiomaFormatado, livro.getNumeroDownloads());
        System.out.println(exibeLivro);
    }


    private void listarHistoricoDeLivros() {
        List<Livro> listaLivro = livroRepositorio.findAll();
        listaLivro.forEach(this::exibeDadosLivros);
    }

    public void exibeDadosAutores(Autor autor) {
        var exibeAutor = """
                \n******* AUTOR *******
                \nNome: %s
                \nNascido em: %d
                \nFalecido em: %d
                \n***********************************\n
                """.formatted(autor.getNome(), autor.getAnoDeNascimento(), autor.getAnoDeFalecimento());
        System.out.println(exibeAutor);
    }

    private void listarHistoricoDeAutores() {
        List<Autor> listaAutores = autorRepositorio.findAll();

        if (listaAutores.isEmpty()) {
            System.out.println("\nNão há autores(as) armazenados!");
        } else {
            System.out.println("\nAutores(as) encontrados:");
            listaAutores.stream().sorted(Comparator.comparing(Autor::getNome)).forEach(this::exibeDadosAutores);
        }
    }

    private int solicitarAno() {
        System.out.println("Digite o ano para consultar autores vivos: ");
        while (true) {
            try {
                int ano = leitura.nextInt();
                leitura.nextLine();
                return ano;
            } catch (InputMismatchException e) {
                System.out.println("Ano inválido. Por favor, digite um número inteiro. EX: 2010");
                leitura.nextLine();
            }
        }
    }

    private void listarAutoresVivos() {
        int ano = solicitarAno();

        if (ano < 0) {
            System.out.println("Ano inválido, tente novamente!");
            return;
        }

        List<Autor> autoresVivosEmAno = livroRepositorio.obterAutoresVivosEmAno(ano);

        System.out.println("\n ***** Esses são os Autores(as) encontrados para o ano informado ----------------");

        autoresVivosEmAno.stream().sorted(Comparator.comparing(Autor::getNome)).forEach(this::exibirAutor);
        if (autoresVivosEmAno.isEmpty()) {
            System.out.println("\nNão foi encontrado nenhum registro para o ano informado!");
        }
    }

    private void exibirAutor(Autor autor) {
        System.out.printf("\nAutor: %s - Nascido: %d - Falecido: %d",
                autor.getNome(), autor.getAnoDeNascimento(), autor.getAnoDeFalecimento());
    }

    private void listarLivrosPorIdioma() {
        String listaDeIdioma = """
            Qual o idioma do livro? 
            
            -> Português                
            -> Inglês
            -> Espanhol
            -> Francês               
            
            Opção:                 
            """;
        System.out.println(listaDeIdioma);
        String text = leitura.nextLine();
        Idiomas idioma = Idiomas.fromPortugues(text);

        List<Livro> livroIdioma = livroRepositorio.findByIdiomas(idioma);

        System.out.println("\n****** Livros encontrados para esse idioma ******");

        livroIdioma.forEach(this::exibeDadosLivros);
    }

    private void listarTop5LivrosBaixados() {
        List<Livro> livros = livroRepositorio.findAll();

        System.out.println("\n********** Os TOP 5 livros mais baixados são ********** ");

        livros.stream()
                .sorted(Comparator.comparingDouble(Livro::getNumeroDownloads).reversed())
                .limit(5)
                .forEach(l -> System.out.printf("\nTítulo: %s - Downloads: %.0f", l.getTitulo(), l.getNumeroDownloads()));
    }

    private void exibeDadosLivroAutor(Livro livro) {
        System.out.printf("Livro: %s\n", livro.getTitulo());
    }

    private void listarAutorPeloNome() {
        System.out.println("Digite um trecho do nome do autor que deseja buscar");
        var trechoNomeAutor = leitura.nextLine();

        List<Autor> autorEncontrado = autorRepositorio.autorPorTrechoDoNome(trechoNomeAutor);

        if (autorEncontrado.isEmpty()) {
            System.out.println("\nNão foi possível localizar nenhum autor com esse nome!");
        } else {
            autorEncontrado.forEach(a -> System.out.printf("\nAutor encontrado: %s\n", a.getNome()));

            System.out.println("********** LIVROS ENCONTRADOS ********** ");

            for (Autor autor : autorEncontrado) {
                List<Livro> livrosDoAutor = autor.getLivros();

                if (!livrosDoAutor.isEmpty()) {
                    livrosDoAutor.forEach(this::exibeDadosLivroAutor);
                } else {
                    System.out.printf("\nO autor %s não possui livros cadastrados.\n", autor.getNome());
                }
            }
        }
    }
}
