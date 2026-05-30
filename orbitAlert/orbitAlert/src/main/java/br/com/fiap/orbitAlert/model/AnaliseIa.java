package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ANALISE_IA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnaliseIa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_analise_ia")
    @SequenceGenerator(name = "seq_analise_ia", sequenceName = "SEQ_ANALISE_IA", allocationSize = 1)
    @Column(name = "ID_ANALISE")
    private Long id;

    @Column(name = "DS_ANALISE", nullable = false, length = 4000)
    private String descricaoAnalise;

    @Column(name = "NM_MODELO_IA", length = 100)
    private String modeloIa;

    @Column(name = "NR_CONFIANCA")
    private Double confianca;

    @Column(name = "DT_ANALISE")
    private LocalDateTime dataAnalise;

    @OneToOne
    @JoinColumn(name = "ID_ALERTA", nullable = false, unique = true)
    private Alerta alerta;
}