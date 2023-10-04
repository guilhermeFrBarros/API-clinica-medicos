package med.prometheus.api.domain.consulta.validacoes;

import med.prometheus.api.domain.ValidacaoException;
import med.prometheus.api.domain.consulta.DadosAgendamentoConsulta;
import med.prometheus.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements  ValidadorAgendamentoDeConsulta  {

    @Autowired
    private PacienteRepository repository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) throws ValidacaoException {
        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}