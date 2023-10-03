package med.prometheus.api.domain.consulta;

import jakarta.persistence.*;
import lombok.*;
import med.prometheus.api.domain.medico.Medico;
import med.prometheus.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( of = "id" )
@Entity
@Table( name = "consultas" )
public class Consulta {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime data;

    @Column(name = "motivo_cancelamento")
    @Enumerated( EnumType.STRING )
    private MotivoCancelamento motivoDoCancelamento;

    public void cancela(DadosCancelamentoConsulta dados) {
        this.motivoDoCancelamento = dados.motivoCancelamento();
    }
}
