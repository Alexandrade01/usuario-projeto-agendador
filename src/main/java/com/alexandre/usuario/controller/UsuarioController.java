package com.alexandre.usuario.controller;

import com.alexandre.usuario.business.UsuarioService;
import com.alexandre.usuario.business.dto.EnderecoDTO;
import com.alexandre.usuario.business.dto.TelefoneDTO;
import com.alexandre.usuario.business.dto.UsuarioDTO;
import com.alexandre.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> postUsuario(@RequestBody UsuarioDTO usuarioDTO){

        return  ResponseEntity.ok(usuarioService.salvaUsuarioDTO(usuarioDTO));

    }

    @PostMapping("login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {

        Authentication authentication = authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())

        );

        return "Bearer: " + jwtUtil.generateToken(authentication.getName());

    }

    @GetMapping("getByEmail")
    public ResponseEntity<UsuarioDTO> getUsuarioPorEmail(@RequestParam String email) {

        return ResponseEntity.ok(usuarioService.buscaUsuarioPorEmail(email));

    }

    @DeleteMapping("deleteByEmail/{email}")
    public ResponseEntity<String> deleteusuarioPorEmail(@PathVariable String email) {

        usuarioService.deleteByEmail(email);

        return ResponseEntity.ok().body("Usuario com email " + email + " deletado !");

    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                                           @RequestHeader("Authorization") String token){

        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token,dto));

    }

    @GetMapping("findAll")
    public ResponseEntity<List<UsuarioDTO>> findAllUsuario(){

        return ResponseEntity.ok(usuarioService.findAllUsuarios());
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                        @RequestParam("id") Long id){

        return ResponseEntity.ok(usuarioService.atualizaEndereco(id,enderecoDTO));

    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO telefoneDto,
                                                        @RequestParam("id") Long id){

        return ResponseEntity.ok(usuarioService.atualizaTelefone(id,telefoneDto));

    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastroDeEndereco(token,dto));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastroDeTelefone(token,dto));
    }

}
