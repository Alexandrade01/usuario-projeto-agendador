
package com.alexandre.usuario.controller;

import com.alexandre.usuario.business.UsuarioService;
import com.alexandre.usuario.business.dto.EnderecoDTO;
import com.alexandre.usuario.business.dto.TelefoneDTO;
import com.alexandre.usuario.business.dto.UsuarioDTO;
import com.alexandre.usuario.infrastructure.security.JwtUtil;
import com.alexandre.usuario.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** * Controller REST responsável pelo gerenciamento de usuários e seus dados relacionados. * <p> * Fornece endpoints para operações CRUD de usuários, endereços e telefones, * além de autenticação via JWT. * </p> * * @author Alexandre * @version 1.0 * @since 2026-04-25 */
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**     * Cadastra um novo usuário no sistema.     *     * @param usuarioDTO objeto contendo os dados do usuário a ser cadastrado     * @return ResponseEntity contendo o DTO do usuário cadastrado com status 200 OK     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> postUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        return ResponseEntity.ok(usuarioService.salvaUsuarioDTO(usuarioDTO));

    }

    /**     * Realiza a autenticação do usuário e retorna um token JWT.     *     * @param usuarioDTO objeto contendo email e senha do usuário     * @return ResponseEntity contendo o token JWT prefixado com "Bearer" com status 200 OK     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) {

        return ResponseEntity.ok(usuarioService.autenticarUsuario(usuarioDTO));

    }

    /**     * Busca um usuário pelo endereço de email.     *     * @param email endereço de email do usuário a ser buscado (query parameter)     * @return ResponseEntity contendo o DTO do usuário encontrado com status 200 OK     */
    @GetMapping("/getByEmail")
    public ResponseEntity<UsuarioDTO> getUsuarioPorEmail(@RequestParam String email) {

        return ResponseEntity.ok(usuarioService.buscaUsuarioPorEmail(email));

    }

    /**     * Remove um usuário do sistema pelo endereço de email.     *     * @param email endereço de email do usuário a ser deletado (path variable)     * @return ResponseEntity contendo mensagem de confirmação com status 200 OK     */
    @DeleteMapping("/deleteByEmail/{email}")
    public ResponseEntity<String> deleteusuarioPorEmail(@PathVariable String email) {

        usuarioService.deleteByEmail(email);

        return ResponseEntity.ok().body("Usuario com email " + email + " deletado !");

    }

    /**     * Atualiza os dados de um usuário autenticado.     *     * @param dto objeto contendo os novos dados do usuário     * @param token token JWT de autenticação (header Authorization)     * @return ResponseEntity contendo o DTO do usuário atualizado com status 200 OK     */
    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                                           @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));

    }

    /**     * Atualiza os dados de um endereço específico.     *     * @param enderecoDTO objeto contendo os novos dados do endereço     * @param id identificador do endereço a ser atualizado (query parameter)     * @param token token JWT de autenticação (header Authorization)     * @return ResponseEntity contendo o DTO do endereço atualizado com status 200 OK     */
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                        @RequestParam("id") Long id) {

        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, enderecoDTO));

    }

    /**     * Atualiza os dados de um telefone específico.     *     * @param telefoneDto objeto contendo os novos dados do telefone     * @param id identificador do telefone a ser atualizado (query parameter)     * @param token token JWT de autenticação (header Authorization)     * @return ResponseEntity contendo o DTO do telefone atualizado com status 200 OK     */
    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO telefoneDto,
                                                        @RequestParam("id") Long id) {

        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, telefoneDto));

    }

    /**     * Cadastra um novo endereço para o usuário autenticado.     *     * @param dto objeto contendo os dados do endereço a ser cadastrado     * @param token token JWT de autenticação (header Authorization)     * @return ResponseEntity contendo o DTO do endereço cadastrado com status 200 OK     */
    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastroDeEndereco(token, dto));
    }

    /**     * Cadastra um novo telefone para o usuário autenticado.     *     * @param dto objeto contendo os dados do telefone a ser cadastrado     * @param token token JWT de autenticação (header Authorization)     * @return ResponseEntity contendo o DTO do telefone cadastrado com status 200 OK     */
    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastroDeTelefone(token, dto));
    }

    /**     * Lista todos os usuários cadastrados no sistema.     * <p>     * Requer autenticação via token JWT.     * </p>     *     * @param token token JWT de autenticação (header Authorization)     * @return ResponseEntity contendo lista de DTOs de todos os usuários com status 200 OK     */
    @GetMapping("/findAll")
    public ResponseEntity<List<UsuarioDTO>> findAllUsuario(@RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(usuarioService.findAllUsuarios(token));
    }

    /**     * Remove um endereço específico do usuário autenticado.     *     * @param enderecoId identificador do endereço a ser deletado (path variable)     * @param token token JWT de autenticação (header Authorization)     * @return ResponseEntity contendo mensagem de confirmação com status 200 OK     */
    @DeleteMapping("/deleteByEndereco/{enderecoId}")
    public ResponseEntity<String> exclusaoDeEndereco(@PathVariable Long enderecoId, @RequestHeader("Authorization") String token) {


        usuarioService.deleteByEndereco(token,enderecoId);

        return ResponseEntity.ok().body("Endereço deletado !");
    }

    /**     * Remove um telefone específico do usuário autenticado.     *     * @param telefoneId identificador do telefone a ser deletado (path variable)     * @param token token JWT de autenticação (header Authorization)     * @return ResponseEntity contendo mensagem de confirmação com status 200 OK     */
    @DeleteMapping("/deleteByTelefone/{telefoneId}")
    public ResponseEntity<String> exclusaoDeTelefone(@PathVariable Long telefoneId, @RequestHeader("Authorization") String token) {


        usuarioService.deleteByTelefone(token, telefoneId);

        return ResponseEntity.ok("Telefone deletado !");
    }

}