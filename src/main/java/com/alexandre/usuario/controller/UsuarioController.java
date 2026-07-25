
package com.alexandre.usuario.controller;

import com.alexandre.usuario.business.UsuarioService;
import com.alexandre.usuario.business.ViaCepService;
import com.alexandre.usuario.business.dto.EnderecoDTO;
import com.alexandre.usuario.business.dto.TelefoneDTO;
import com.alexandre.usuario.business.dto.UsuarioDTO;
import com.alexandre.usuario.infrastructure.clients.ViaCepDTO;
import com.alexandre.usuario.infrastructure.exception.dto.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsável pelo gerenciamento de usuários e seus dados relacionados.
 * <p>
 * Fornece endpoints para operações CRUD de usuários, endereços e telefones,
 * além de autenticação via JWT.
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 * @since 2026-04-25
 */
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "API para gerenciamento completo de usuários, endereços e telefones")
@SecurityRequirement(name = "bearer-jwt")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ViaCepService viaCepService;

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

    /**
     * Busca um usuário pelo endereço de email.
     *
     * @param email endereço de email do usuário a ser buscado (query parameter)
     * @return ResponseEntity contendo o DTO do usuário encontrado com status 200 OK
     */
    @Operation(
            summary = "Buscar usuário por email",
            description = "Retorna os dados completos de um usuário específico através do seu email. Requer autenticação.",
            tags = {"Usuários"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token inválido ou expirado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/getByEmail")
    public ResponseEntity<UsuarioDTO> getUsuarioPorEmail(
            @Parameter(description = "Email do usuário a ser buscado", required = true, example = "joao.silva@email.com")
            @RequestParam String email) {

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

    /**
     * Atualiza os dados de um endereço específico.
     *
     * @param enderecoDTO objeto contendo os novos dados do endereço
     * @param id identificador do endereço a ser atualizado (query parameter)
     * @return ResponseEntity contendo o DTO do endereço atualizado com status 200 OK
     */
    @Operation(
            summary = "Atualizar endereço",
            description = "Atualiza os dados de um endereço específico do usuário autenticado. Requer autenticação.",
            tags = {"Endereços"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EnderecoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Endereço não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token inválido ou expirado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(
            @Parameter(description = "Novos dados do endereço", required = true)
            @RequestBody EnderecoDTO enderecoDTO,
            @Parameter(description = "ID do endereço a ser atualizado", required = true, example = "1")
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

    /**
     * Cadastra um novo telefone para o usuário autenticado.
     *
     * @param dto objeto contendo os dados do telefone a ser cadastrado
     * @param token token JWT de autenticação (header Authorization)
     * @return ResponseEntity contendo o DTO do telefone cadastrado com status 200 OK
     */
    @Operation(
            summary = "Cadastrar novo telefone",
            description = "Adiciona um novo telefone ao usuário autenticado. O usuário é identificado através do token JWT.",
            tags = {"Telefones"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Telefone cadastrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TelefoneDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token inválido ou expirado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados do telefone inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(
            @Parameter(description = "Dados do telefone a ser cadastrado", required = true)
            @RequestBody TelefoneDTO dto,
            @Parameter(description = "Token JWT no formato: Bearer {token}", required = true)
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

    /**
     * Remove um telefone específico do usuário autenticado.
     *
     * @param telefoneId identificador do telefone a ser deletado (path variable)
     * @param token token JWT de autenticação (header Authorization)
     * @return ResponseEntity contendo mensagem de confirmação com status 200 OK
     */
    @Operation(
            summary = "Deletar telefone",
            description = "Remove permanentemente um telefone específico do usuário autenticado. Requer autenticação.",
            tags = {"Telefones"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Telefone deletado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Telefone deletado !")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Telefone não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token inválido ou expirado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @DeleteMapping("/deleteByTelefone/{telefoneId}")
    public ResponseEntity<String> exclusaoDeTelefone(
            @Parameter(description = "ID do telefone a ser deletado", required = true, example = "1")
            @PathVariable Long telefoneId,
            @Parameter(description = "Token JWT no formato: Bearer {token}", required = true)
            @RequestHeader("Authorization") String token) {


        usuarioService.deleteByTelefone(token, telefoneId);

        return ResponseEntity.ok("Telefone deletado !");
    }

    @GetMapping("/endereco/{cep}")
    public ResponseEntity<ViaCepDTO> buscarDadosCEP(@PathVariable("cep") String cep) {

        return ResponseEntity.ok(viaCepService.buscaCEP(cep));

    }

}