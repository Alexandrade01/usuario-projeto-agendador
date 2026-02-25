package com.alexandre.usuario.controller;

import com.alexandre.usuario.business.UsuarioService;
import com.alexandre.usuario.business.dto.UsuarioDTO;
import com.alexandre.usuario.infrastructure.entity.Usuario;
import com.alexandre.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Usuario> getUsuarioPorEmail(@RequestParam String email) {

        return ResponseEntity.ok(usuarioService.buscaUsuarioPorEmail(email));

    }

    @DeleteMapping("deleteByEmail/{email}")
    public ResponseEntity<String> deleteusuarioPorEmail(@PathVariable String email) {

        usuarioService.deleteByEmail(email);

        return ResponseEntity.ok().body("Usuario com email " + email + " deletado !");

    }

}
