package med.prometheus.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.prometheus.api.domain.endereco.DadosEndereco;

public record DadosAtualizaMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
