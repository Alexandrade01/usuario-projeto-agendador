package com.alexandre.usuario.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Dados do telefone")
public class TelefoneDTO {

    @Schema(description = "Identificador único do telefone", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Número do telefone (sem DDD)", example = "987654321", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 10)
    @Column(name = "numero",length = 10)
    private String numero;

    @Schema(description = "DDD do telefone", example = "11", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 3)
    @Column(name = "ddd",length = 3)
    private String ddd;
}
