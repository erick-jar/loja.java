package br.com.senac.loja.dto;
//request é o pedido. ele vai enviar dados para o backend.
// Response é a resposta do backend para o frontend.

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "Informe o nome do produto.")
        @Size(max = 100, message = "O nome do produto não pode exceder 100 caracteres.")
        String nome,

        @Size(max = 255, message = "A descrição do produto não pode exceder 255 caracteres.")
    String descricao,

@NotNull(message = "Informe o preço do produto.")
@DecimalMin(value = "0.01", message = "O preço do produto deve ser maior que zero.")
BigDecimal preco,

    @NotNull (message = "Informe a quantidade do produto.")
    @Min(value = 0, message = "A quantidade do produto deve ser um número não negativo.")
    Integer quantidade,

    @NotNull (message = "Informe a categoria do produto.")
    Long categoriaId

){}
