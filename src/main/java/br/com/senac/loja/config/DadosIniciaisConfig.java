package br.com.senac.loja.config;

import br.com.senac.loja.model.Categoria;
import br.com.senac.loja.model.Produto;
import br.com.senac.loja.repository.CategoriaRepository;
import br.com.senac.loja.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DadosIniciaisConfig {

    // adiciona duas categoria quando o database estiver vazio.
    @Bean
    CommandLineRunner carregarCategorias(CategoriaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Categoria(
                    "Informática",
                    "Computadores, acessórios e periféricos"
                ));
                repository.save(new Categoria(
                    "Escritório",
                    "Materiais e equipamentos de escritório"
                ));
            }
        };
    }
}
