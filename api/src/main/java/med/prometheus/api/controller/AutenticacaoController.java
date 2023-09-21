package med.prometheus.api.controller;

import jakarta.validation.Valid;
import med.prometheus.api.domain.usuario.DadosAutenticacao;
import med.prometheus.api.domain.usuario.Usuario;
import med.prometheus.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;  // chama a classe indiretamente AutenticacaoService

    @Autowired
    private TokenService tokenService;
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados ) {
        var DTOSpringSecurity = new UsernamePasswordAuthenticationToken( dados.login(), dados.senha() );
        //  ^ está guardando objeto
        var authentication = manager.authenticate(DTOSpringSecurity);

        var token = tokenService.gerarToken( (Usuario) authentication.getPrincipal() );
        return ResponseEntity.ok( token );
    }
}
