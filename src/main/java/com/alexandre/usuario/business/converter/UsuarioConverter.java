package com.alexandre.usuario.business.converter;

import com.alexandre.usuario.business.dto.EnderecoDTO;
import com.alexandre.usuario.business.dto.TelefoneDTO;
import com.alexandre.usuario.business.dto.UsuarioDTO;
import com.alexandre.usuario.infrastructure.entity.Endereco;
import com.alexandre.usuario.infrastructure.entity.Telefone;
import com.alexandre.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {

        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();

    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {

        //alternativa para for e foreach
        return enderecoDTOS.stream().map(this::getEndereco).toList();
    }

    public Endereco getEndereco(EnderecoDTO enderecoDTO) {

        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();

    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS) {

        return telefoneDTOS.stream().map(this::paraTelefone).toList();

    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {

        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {

        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuario.getTelefones()))
                .build();

    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> endereco) {

        //alternativa para for e foreach
        return endereco.stream().map(this::paraEnderecosDTO).toList();
    }

    public EnderecoDTO paraEnderecosDTO(Endereco endereco) {

        return EnderecoDTO.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();

    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefone) {

        return telefone.stream().map(this::paraTelefoneDTO).toList();

    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {

        return TelefoneDTO.builder()
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }
}
