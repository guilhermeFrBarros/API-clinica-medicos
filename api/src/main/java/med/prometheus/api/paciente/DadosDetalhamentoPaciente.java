package med.prometheus.api.paciente;

import med.prometheus.api.endereco.Endereco;

public record DadosDetalhamentoPaciente(Long id,
                                        String nome,
                                        String email,
                                        String telefone,
                                        String CPF,
                                        Endereco endereco) {
    public  DadosDetalhamentoPaciente( Paciente paciente ) {
        this( paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCPF(), paciente.getEndereco() );
    }
}
