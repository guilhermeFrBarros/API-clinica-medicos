package med.prometheus.api.domain.consulta;

import med.prometheus.api.domain.ValidacaoException;
import med.prometheus.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.prometheus.api.domain.medico.Medico;
import med.prometheus.api.domain.medico.MedicoRepository;
import med.prometheus.api.domain.paciente.Paciente;
import med.prometheus.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired // injeta todos os validadores que implementa a Inteface ValidadorAgendamentoDeConsulta
    List <ValidadorAgendamentoDeConsulta> validadores;
    public DadosDetalhamentoConsulta agendar( DadosAgendamentoConsulta dados ) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        // veriafica se vei o id do medico e se o medico existe
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validadores.forEach( v -> v.validar( dados ));

        Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());  // serve para acessar os dasdos ds pacientes acessados
//        var paciente2 = pacienteRepository.findById(dados.idPaciente()).get(); -> serve para quando vc que manipular o paciente retonrado
        var medico = escolherMedico( dados );
        if (medico == null) {
            throw new ValidacaoException("Não existe Medico disponinvel nessa Dada");
        }

        Consulta consulta = new Consulta( null, medico, paciente, dados.data(), null );
        consultaRepository.save( consulta );

        return new DadosDetalhamentoConsulta( consulta );
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return  medicoRepository.getReferenceById(dados.idMedico() );
        }

        if (dados.especialidade() == null) {
            throw  new ValidacaoException(" Especialidade é Obrigatoria quando médico nãop definida ");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData( dados.especialidade(), dados.data() );
    }


    public void cancelar(DadosCancelamentoConsulta dados) {
        if ( !consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException(" Id da Consulta inexistene ");
        }

        Consulta consulta = consultaRepository.findById(dados.idConsulta()).get();
        // retorna um optinal se não existir

        LocalDateTime dataMinima = consulta.getData();
        System.out.println(dataMinima);
        if ( LocalDateTime.now().isAfter(dataMinima.minusHours(24) ) ) {
            throw new ValidacaoException("Data de alteracao de no mimimo 24H antes da comnsulta");
        }
//        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancela( dados);


    }
}
