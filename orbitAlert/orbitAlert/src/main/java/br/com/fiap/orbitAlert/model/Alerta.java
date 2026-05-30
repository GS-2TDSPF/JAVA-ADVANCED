package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_ALERTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alerta")
    @SequenceGenerator(name = "seq_alerta", sequenceName = "SEQ_ALERTA", allocationSize = 1)
    @Column(name = "ID_ALERTA")
    private Long id;

    @Column(name = "NR_INDICE_RISCO", nullable = false)
    private Integer indiceRisco; // 1 a 5

    @Column(name = "ST_STATUS", nullable = false, length = 20)
    private String status; // ATIVO, MONITORANDO, ENCERRADO

    @Column(name = "DS_ORIGEM", length = 20)
    private String origem; // IA, IOT, MANUAL

    @Column(name = "DT_CRIACAO")
    private LocalDateTime dataCriacao;

    @Column(name = "DT_ENCERRAMENTO")
    private LocalDateTime dataEncerramento;

    @ManyToOne
    @JoinColumn(name = "ID_ZONA_RISCO", nullable = false)
    private ZonaRisco zonaRisco;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_ALERTA", nullable = false)
    private TipoAlerta tipoAlerta;

    @OneToOne(mappedBy = "alerta", cascade = CascadeType.ALL)
    private AnaliseIa analiseIa;

    @OneToMany(mappedBy = "alerta", cascade = CascadeType.ALL)
    private List<HistoricoAlerta> historicos;

    @OneToMany(mappedBy = "alerta", cascade = CascadeType.ALL)
    private List<Notificacao> notificacoes;
}