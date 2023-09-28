package med.prometheus.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.prometheus.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import med.prometheus.api.infra.security.TokenService;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken( request );


        if ( tokenJWT != null ) {
            var subject = tokenService.getSubject( tokenJWT );
            var usuario =  repository.findByLogin(subject);


            var authentication = new
                    UsernamePasswordAuthenticationToken( usuario, null, usuario.getAuthorities() );
            SecurityContextHolder.getContext()
                    .setAuthentication( authentication );

        }

        filterChain.doFilter(request, response);

    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) { // verifica se o token veio, se veio
            return  authorizationHeader.replace("Bearer ", "");  // retorna o token
        }

        return null; // se não retona null
    }
}
