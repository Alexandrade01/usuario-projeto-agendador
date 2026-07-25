package com.alexandre.usuario.business;

import com.alexandre.usuario.infrastructure.clients.ViaCepClient;
import com.alexandre.usuario.infrastructure.clients.ViaCepDTO;
import com.alexandre.usuario.infrastructure.exception.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient client;

    public ViaCepDTO buscaCEP(String cep) {

        return client.buscaDadosEnderecoCEP(processarCEP(cep));
    }

    private String processarCEP(String cep){

        String cepFormatado = cep.replace("-", "").replace(".", "").replace(" ", "");

        // "\\d+" -> apenas numericos e mais que um numero
        if(!cepFormatado.matches("\\d+") || cepFormatado.length() != 8){

            throw new IllegalArgumentException("O CEP está em um formato ilegal, por favor verifique as informações");

        }

        return cepFormatado;

    }
}
