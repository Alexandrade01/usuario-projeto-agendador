package com.alexandre.usuario.business;

import com.alexandre.usuario.business.converter.UsuarioConverter;
import com.alexandre.usuario.business.dto.UsuarioDTO;
import com.alexandre.usuario.infrastructure.entity.Usuario;
import com.alexandre.usuario.infrastructure.exception.ConflictException;
import com.alexandre.usuario.infrastructure.exception.ResourceNotFoundException;
import com.alexandre.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuarioDTO(UsuarioDTO usuarioDTO){

        validadorEmail(usuarioDTO.getEmail());

        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        //Objeto Usuario -> entity encapsulamento para a tabela no postgree
        //Objeto UsuarioDTO -> usuario local para uso encapsulado
        Usuario user = usuarioConverter.paraUsuario(usuarioDTO);

        user = repository.save(user);

        return usuarioConverter.paraUsuarioDTO(user);
    }

    public boolean verificaEmailExistente(String email) {


        return repository.existsByEmail(email);
    }

    public void validadorEmail(String email) {

        try {
            boolean existe = verificaEmailExistente(email);

            if (existe) {
                throw new ConflictException("Email de usuario já cadastrado: " + email);
            }
        } catch (ConflictException e) {

            throw new ConflictException("Email de usuario já cadastrado: " + email, e.getCause());
        }

    }

    public Usuario buscaUsuarioPorEmail(String email) {

        return repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email));
    }

    /**
     * @param email
     */
    public void deleteByEmail(String email) {

        repository.deleteByEmail(email);
    }
}
