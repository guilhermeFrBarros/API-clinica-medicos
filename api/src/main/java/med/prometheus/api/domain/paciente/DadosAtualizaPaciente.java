package med.prometheus.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.prometheus.api.domain.endereco.DadosEndereco;

public record DadosAtualizaPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
