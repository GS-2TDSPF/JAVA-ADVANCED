package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "TB_ZONA_RISCO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZonaRisco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_zona_risco")
    @SequenceGenerator(name = "seq_zona_risco", sequenceName = "SEQ_ZONA_RISCO", allocationSize = 1)
    @Column(name = "ID_ZONA_RISCO")
    private Long id;

    @Column(name = "NM_ZONA", nullable = false, length = 200)
    private String nome;

    @Column(name = "DS_DESCRICAO", length = 500)
    private String descricao;

    @Column(name = "NR_LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "NR_LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "NR_AREA_KM2")
    private Double areaKm2;

    @Column(name = "ST_ATIVO", length = 1)
    private String statusAtivo = "S";

    @ManyToOne
    @JoinColumn(name = "ID_MUNICIPIO", nullable = false)
    private Municipio municipio;

    @OneToMany(mappedBy = "zonaRisco", cascade = CascadeType.ALL)
    private List<EstacaoIot> estacoesIot;

    @OneToMany(mappedBy = "zonaRisco", cascade = CascadeType.ALL)
    private List<Alerta> alertas;
}