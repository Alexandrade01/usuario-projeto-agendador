package com.alexandre.usuario.business;

import com.alexandre.usuario.business.converter.UsuarioConverter;
import com.alexandre.usuario.business.dto.UsuarioDTO;
import com.alexandre.usuario.infrastructure.entity.Usuario;
import com.alexandre.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuarioDTO(UsuarioDTO usuarioDTO){
        //Objeto Usuario -> entity
        //Objeto UsuarioDTO -> usuario local para uso encapsulado
        Usuario user = usuarioConverter.paraUsuario(usuarioDTO);

        user = repository.save(user);

        return usuarioConverter.paraUsuarioDTO(user);
    }
}
