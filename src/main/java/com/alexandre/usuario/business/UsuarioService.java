package com.alexandre.usuario.business;

import com.alexandre.usuario.business.converter.UsuarioConverter;
import com.alexandre.usuario.business.dto.UsuarioDTO;
import com.alexandre.usuario.infrastructure.entity.Usuario;
import com.alexandre.usuario.infrastructure.exception.ConflictException;
import com.alexandre.usuario.infrastructure.exception.ResourceNotFoundException;
import com.alexandre.usuario.infrastructure.repository.UsuarioRepository;
import com.alexandre.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    //Atualizacao dos dados da tabela usuario
    // A orientação é buscar o usuario via token
    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){

        //Buscamos o email do usuario atraves do token
        String email = jwtUtil.extractTokenEmail(token.substring(7));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null );
        //busca os dados do usuario
        Usuario usuarioEntity = repository.findByEmail(email).orElseThrow(()
                -> new ResourceNotFoundException("Email não localizado !"));

        //Faz a mesclagem dos dados da tabela e dos dados novos
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO,usuarioEntity);

        //encriptar novamente a senha
        usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));

        //Salva os novos dados na tabela (USUARIO) e envia os novos dados no modo DTO para o controller (USUARIODTO)
        return usuarioConverter.paraUsuarioDTO(repository.save(usuario));
    }

    public List<UsuarioDTO> findAllUsuarios(){

        List<Usuario> usuarioList = repository.findAll();

        if(usuarioList.isEmpty()){

            throw new ResourceNotFoundException("Nenhum usuario encontrado !");

        }

        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();

        usuarioList.forEach(usuario -> usuarioDTOList.add(usuarioConverter.paraUsuarioDTO(usuario)));

        return usuarioDTOList;

    }
}
