package med.prometheus.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import med.prometheus.api.medico.DadosDetalhamentoMedico;
import med.prometheus.api.medico.DadosListagemMedico;
import med.prometheus.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosPaciente, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente( dadosPaciente );
        repository.save(paciente);

        var uri = uriBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body( new DadosDetalhamentoPaciente( paciente ));
    }

    @GetMapping
    public ResponseEntity< Page<DadosListagemPaciente> > listar( @PageableDefault( size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemPaciente> pagePacienteDTO = repository
                .findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);

        return ResponseEntity.ok( pagePacienteDTO );
    }

    @Transactional
    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizaPaciente dadosAtualizaPaciente){

        var paciente = repository.getReferenceById( dadosAtualizaPaciente.id() );
        try {
            paciente.atualizarDados( dadosAtualizaPaciente );
            return new ResponseEntity( new DadosDetalhamentoPaciente( paciente ), HttpStatus.OK);
        }
        catch (EntityNotFoundException exception) {
            return new ResponseEntity( "ERRO SERVER", HttpStatus.INTERNAL_SERVER_ERROR); // codigo ERRO no servidor
        }
    }

    @DeleteMapping("/desativar/{id}")
    @Transactional
    public ResponseEntity excluir( @PathVariable Long id) {
        var paciente = repository.getReferenceById( id );
        paciente.excluir();

        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar( @PathVariable Long id ) {
        var paciente = repository.getReferenceById( id );
        return ResponseEntity.ok( new DadosDetalhamentoPaciente( paciente ));
    }

    // TESTE DE INVASÃO
//    @PostMapping("/t")
//    public void cadastrar2( @RequestBody  Paciente dadosPaciente ) {
//        repository.save(dadosPaciente);
//    }
}
