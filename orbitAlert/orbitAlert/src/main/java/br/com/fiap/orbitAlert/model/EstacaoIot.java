package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_ESTACAO_IOT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstacaoIot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estacao_iot")
    @SequenceGenerator(name = "seq_estacao_iot", sequenceName = "SEQ_ESTACAO_IOT", allocationSize = 1)
    @Column(name = "ID_ESTACAO")
    private Long id;

    @Column(name = "NM_ESTACAO", nullable = false, length = 150)
    private String nome;

    @Column(name = "DS_TOPICO_MQTT", nullable = false, length = 200)
    private String topicoMqtt;

    @Column(name = "ST_STATUS", length = 20)
    private String status; // ATIVA, INATIVA, MANUTENCAO

    @Column(name = "DT_INSTALACAO")
    private LocalDateTime dataInstalacao;

    @ManyToOne
    @JoinColumn(name = "ID_ZONA_RISCO", nullable = false)
    private ZonaRisco zonaRisco;

    @OneToMany(mappedBy = "estacaoIot", cascade = CascadeType.ALL)
    private List<LeituraIot> leituras;
}