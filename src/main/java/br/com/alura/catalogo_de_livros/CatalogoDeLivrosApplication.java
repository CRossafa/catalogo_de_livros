package br.com.alura.catalogo_de_livros;

import br.com.alura.catalogo_de_livros.principal.Principal;
import br.com.alura.catalogo_de_livros.repository.AutorRepository;
import br.com.alura.catalogo_de_livros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogoDeLivrosApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository livroRepositorio;
	@Autowired
	private AutorRepository autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(CatalogoDeLivrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(livroRepositorio, autorRepositorio);
		principal.exibeMenu();
	}
}
