package com.alexandre.usuario.business.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {

    @Column(name = "numero",length = 10)
    private String numero;

    @Column(name = "ddd",length = 3)
    private String ddd;
}
