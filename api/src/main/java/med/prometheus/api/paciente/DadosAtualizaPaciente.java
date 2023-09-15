package med.prometheus.api.paciente;

import jakarta.validation.constraints.NotNull;
import med.prometheus.api.endereco.DadosEndereco;
import med.prometheus.api.endereco.Endereco;

public record DadosAtualizaPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
