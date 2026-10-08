package br.com.senac.loja.dto;

import br.com.senac.loja.model.Produto;

import java.math.BigDecimal;
// utilizar o record para criar um DTO de resposta para o produto. O record é uma forma mais concisa de criar classes imutáveis em Java, que são ideais para representar dados que não mudam após a criação do objeto. Ele gera automaticamente os métodos equals(), hashCode() e toString(), além dos getters para os campos.

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidade,
        CategoriaResponse categoria
) {
    public static ProdutoResponse from(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidade(),
                CategoriaResponse.from(produto.getCategoria())
        );

    }
}
