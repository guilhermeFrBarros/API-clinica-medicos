package med.prometheus.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAutenticacao(@NotBlank String login, @NotNull String senha ) {
}
