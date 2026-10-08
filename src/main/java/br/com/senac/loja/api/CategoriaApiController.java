package br.com.senac.loja.api;

import br.com.senac.loja.dto.CategoriaResponse;
import br.com.senac.loja.service.CategoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaApiController {
    private final CategoriaService categoriaService;

    public CategoriaApiController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }
    @GetMapping
    public List<CategoriaResponse> listarCategorias() {
        return categoriaService.listar().stream()
            .map(CategoriaResponse::from)
            .toList();
    }
    @GetMapping("/{id}")
    public CategoriaResponse buscar(@PathVariable Long id) {
        return CategoriaResponse.from(categoriaService.buscar(id));
    }
}
