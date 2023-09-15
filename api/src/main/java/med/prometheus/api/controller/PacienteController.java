package med.prometheus.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import med.prometheus.api.medico.DadosListagemMedico;
import med.prometheus.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    public void cadastrar( @RequestBody @Valid DadosCadastroPaciente dadosPaciente ) {
        repository.save(new Paciente(dadosPaciente));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar( @PageableDefault( size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemPaciente> pagePacienteDTO = repository
                .findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);

        return pagePacienteDTO;
    }

    @Transactional
    @PutMapping
    public String atualizar(@RequestBody @Valid DadosAtualizaPaciente dadosAtualizaPaciente){

        var paciente = repository.getReferenceById( dadosAtualizaPaciente.id() );
        try {
            paciente.atualizarDados( dadosAtualizaPaciente );
            return "Atualizado com Sucesso";
        }
        catch (EntityNotFoundException exception) {
            return "erro id inexistente";
        }
    }

    @DeleteMapping("/desativar/{id}")
    @Transactional
    public void excluir( @PathVariable Long id) {
        var paciente = repository.getReferenceById( id );
        paciente.excluir();
    }

    // TESTE DE INVASÃO
//    @PostMapping("/t")
//    public void cadastrar2( @RequestBody  Paciente dadosPaciente ) {
//        repository.save(dadosPaciente);
//    }
}
