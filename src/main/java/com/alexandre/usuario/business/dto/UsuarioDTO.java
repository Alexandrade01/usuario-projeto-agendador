package com.alexandre.usuario.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Dados do usuário")
public class UsuarioDTO {

    @Schema(description = "Nome completo do usuário", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;
    
    @Schema(description = "Endereço de email do usuário (usado como login)", example = "joao.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    
    @Schema(description = "Senha do usuário (min. 6 caracteres)", example = "senha123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String senha;

    @Schema(description = "Lista de endereços do usuário")
    private List<EnderecoDTO> enderecos;
    
    @Schema(description = "Lista de telefones do usuário")
    private List<TelefoneDTO> telefones;

}
