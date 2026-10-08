package br.com.senac.loja.dto;

import br.com.senac.loja.model.Categoria;

// Quando alguem pedir informações de categoria, não queremos devolver a entidade completa, mas sim um DTO com apenas as informações necessárias.
//    Qual a diferença entre Record e DTO?
//DTO significa Data Transfer Object

public record CategoriaResponse(Long id, String nome, String descricao) {
    public static CategoriaResponse from(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

}
