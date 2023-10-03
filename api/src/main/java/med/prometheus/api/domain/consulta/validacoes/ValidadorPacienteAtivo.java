package med.prometheus.api.domain.consulta.validacoes;

import med.prometheus.api.domain.ValidacaoException;
import med.prometheus.api.domain.consulta.DadosAgendamentoConsulta;
import med.prometheus.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidadorPacienteAtivo  {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados) throws ValidacaoException {
        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}