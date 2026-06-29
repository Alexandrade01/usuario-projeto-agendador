package com.alexandre.usuario.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Dados do endereço")
public class EnderecoDTO {

    @Schema(description = "Identificador único do endereço", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Schema(description = "Nome da rua", example = "Rua das Flores", requiredMode = Schema.RequiredMode.REQUIRED)
    private String rua;
    
    @Schema(description = "Número do imóvel", example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long numero;
    
    @Schema(description = "Complemento do endereço", example = "Apartamento 201")
    private String complemento;
    
    @Schema(description = "Bairro", example = "Centro", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bairro;
    
    @Schema(description = "Cidade", example = "São Paulo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cidade;
    
    @Schema(description = "Estado (UF)", example = "SP", requiredMode = Schema.RequiredMode.REQUIRED)
    private String estado;
    
    @Schema(description = "CEP do endereço", example = "12345-678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cep;
}
