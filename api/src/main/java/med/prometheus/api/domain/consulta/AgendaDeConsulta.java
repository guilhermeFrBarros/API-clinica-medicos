package med.prometheus.api.domain.consulta;

import med.prometheus.api.domain.ValidacaoException;
import med.prometheus.api.domain.medico.Medico;
import med.prometheus.api.domain.medico.MedicoRepository;
import med.prometheus.api.domain.paciente.Paciente;
import med.prometheus.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;
    public void agendar( DadosAgendamentoConsulta dados ) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        // veriafica se vei o id do medico e se o emdico existe
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }
        Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());  // serve para acessar os dasdos ds pacientes acessados
//        var paciente2 = pacienteRepository.findById(dados.idPaciente()).get(); -> serve para quando vc que manipular o paciente retonrado
        var medico = escolherMedico( dados );

        Consulta consulta = new Consulta( null, medico, paciente, dados.data() );
        consultaRepository.save( consulta );
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return  medicoRepository.getReferenceById(dados.idMedico() );
        }

        if (dados.especialidade() == null) {
            throw  new ValidacaoException(" Esprcialidade é Obrigatoria quando médico nãop definida ");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData( dados.especialidade(), dados.data() );
    }
}
