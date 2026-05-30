package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_LEITURA_IOT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeituraIot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_leitura_iot")
    @SequenceGenerator(name = "seq_leitura_iot", sequenceName = "SEQ_LEITURA_IOT", allocationSize = 1)
    @Column(name = "ID_LEITURA")
    private Long id;

    @Column(name = "NR_UMIDADE")
    private Double umidade;

    @Column(name = "NR_TEMPERATURA")
    private Double temperatura;

    @Column(name = "NR_CHUVA_MM")
    private Double chuvaMm;

    @Column(name = "DT_LEITURA", nullable = false)
    private LocalDateTime dataLeitura;

    @ManyToOne
    @JoinColumn(name = "ID_ESTACAO", nullable = false)
    private EstacaoIot estacaoIot;
}