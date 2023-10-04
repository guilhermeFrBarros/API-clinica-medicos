package med.prometheus.api.domain.consulta.validacoes;

import med.prometheus.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta  {

    void validar(DadosAgendamentoConsulta dados );
}
