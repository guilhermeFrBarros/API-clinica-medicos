package med.prometheus.api.controller;

import jakarta.validation.Valid;
import med.prometheus.api.domain.consulta.AgendaDeConsulta;
import med.prometheus.api.domain.consulta.DadosAgendamentoConsulta;
import med.prometheus.api.domain.consulta.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaControllers {

    @Autowired
    private AgendaDeConsulta agendaDeConsulta;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        agendaDeConsulta.agendar( dados );

        return ResponseEntity.ok( new DadosDetalhamentoConsulta( null, null, null, null)  );
    }
}
