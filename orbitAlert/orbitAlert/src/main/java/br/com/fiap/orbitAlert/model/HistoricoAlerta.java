package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_HISTORICO_ALERTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoAlerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_historico_alerta")
    @SequenceGenerator(name = "seq_historico_alerta", sequenceName = "SEQ_HISTORICO_ALERTA", allocationSize = 1)
    @Column(name = "ID_HISTORICO")
    private Long id;

    @Column(name = "ST_STATUS_ANTERIOR", length = 20)
    private String statusAnterior;

    @Column(name = "ST_STATUS_NOVO", nullable = false, length = 20)
    private String statusNovo;

    @Column(name = "NR_INDICE_RISCO")
    private Integer indiceRisco;

    @Column(name = "DS_OBSERVACAO", length = 500)
    private String observacao;

    @Column(name = "NM_USUARIO_MOD", length = 150)
    private String nomeUsuarioMod;

    @Column(name = "DT_ALTERACAO")
    private LocalDateTime dataAlteracao;

    @ManyToOne
    @JoinColumn(name = "ID_ALERTA", nullable = false)
    private Alerta alerta;
}