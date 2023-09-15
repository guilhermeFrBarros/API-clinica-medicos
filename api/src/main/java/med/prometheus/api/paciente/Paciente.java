package med.prometheus.api.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.prometheus.api.endereco.Endereco;
import med.prometheus.api.medico.DadosAtualizaMedico;

@Entity
@Table( name = "pacientes" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Paciente {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private Boolean ativo;
    private String telefone;
    private  String CPF;
    private Endereco endereco;

    public Paciente(DadosCadastroPaciente dadosPaciente) {
        this.ativo = true;
        this.nome = dadosPaciente.nome();
        this.email = dadosPaciente.email();
        this.telefone = dadosPaciente.telefone();
        this.CPF = dadosPaciente.CPF();
        this.endereco = new Endereco( dadosPaciente.endereco() );
    }

    public void atualizarDados( DadosAtualizaPaciente dados) {
        if ( dados.nome() != null ) {
            this.nome = dados.nome();
        }
        if ( dados.telefone() != null ) {
            this.telefone = dados.telefone();
        }
        if ( dados.endereco() != null) {
            this.endereco.atualizarDados( dados.endereco() );
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
