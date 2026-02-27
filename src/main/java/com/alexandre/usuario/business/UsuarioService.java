package com.alexandre.usuario.business;

import com.alexandre.usuario.business.converter.UsuarioConverter;
import com.alexandre.usuario.business.dto.EnderecoDTO;
import com.alexandre.usuario.business.dto.TelefoneDTO;
import com.alexandre.usuario.business.dto.UsuarioDTO;
import com.alexandre.usuario.infrastructure.entity.Endereco;
import com.alexandre.usuario.infrastructure.entity.Telefone;
import com.alexandre.usuario.infrastructure.entity.Usuario;
import com.alexandre.usuario.infrastructure.exception.ConflictException;
import com.alexandre.usuario.infrastructure.exception.ResourceNotFoundException;
import com.alexandre.usuario.infrastructure.repository.EnderecoRepository;
import com.alexandre.usuario.infrastructure.repository.TelefoneRepository;
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

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuarioDTO(UsuarioDTO usuarioDTO){

        validadorEmail(usuarioDTO.getEmail());

        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        //Objeto Usuario -> entity encapsulamento para a tabela no postgree
        //Objeto UsuarioDTO -> usuario local para uso encapsulado
        Usuario user = usuarioConverter.paraUsuario(usuarioDTO);

        user = usuarioRepository.save(user);

        return usuarioConverter.paraUsuarioDTO(user);
    }

    public boolean verificaEmailExistente(String email) {


        return usuarioRepository.existsByEmail(email);
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

    public UsuarioDTO buscaUsuarioPorEmail(String email) {

        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() ->
                    new ResourceNotFoundException("Email não encontrado " + email)));
        }
        catch (ResourceNotFoundException e) {

            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    /**
     * @param email
     */
    public void deleteByEmail(String email) {

        usuarioRepository.deleteByEmail(email);
    }

    //Atualizacao dos dados da tabela usuario
    // A orientação é buscar o usuario via token
    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){

        //Buscamos o email do usuario atraves do token
        String email = jwtUtil.extractTokenEmail(token.substring(7));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null );
        //busca os dados do usuario
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(()
                -> new ResourceNotFoundException("Email não localizado !"));

        //Faz a mesclagem dos dados da tabela e dos dados novos
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO,usuarioEntity);

        //encriptar novamente a senha
        usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));

        //Salva os novos dados na tabela (USUARIO) e envia os novos dados no modo DTO para o controller (USUARIODTO)
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public List<UsuarioDTO> findAllUsuarios(){

        List<Usuario> usuarioList = usuarioRepository.findAll();

        if(usuarioList.isEmpty()){

            throw new ResourceNotFoundException("Nenhum usuario encontrado !");

        }

        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();

        usuarioList.forEach(usuario -> usuarioDTOList.add(usuarioConverter.paraUsuarioDTO(usuario)));

        return usuarioDTOList;

    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("Id endereco" +
                " não encontrado " + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO,entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));


    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() -> new ResourceNotFoundException("Id telefone" +
                " não encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO,entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastroDeEndereco(String token, EnderecoDTO dto){

        String email = jwtUtil.extractTokenEmail(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado: " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto,usuario.getId());

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO cadastroDeTelefone(String token, TelefoneDTO dto){

        String email = jwtUtil.extractTokenEmail(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado: " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto,usuario.getId());

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
