package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "TB_MUNICIPIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_municipio")
    @SequenceGenerator(name = "seq_municipio", sequenceName = "SEQ_MUNICIPIO", allocationSize = 1)
    @Column(name = "ID_MUNICIPIO")
    private Long id;

    @Column(name = "NM_MUNICIPIO", nullable = false, length = 150)
    private String nome;

    @Column(name = "SG_ESTADO", nullable = false, length = 2)
    private String siglaEstado;

    @Column(name = "NR_LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "NR_LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "NR_POPULACAO")
    private Integer populacao;

    @Column(name = "ST_ATIVO", length = 1)
    private String statusAtivo = "S";

    @OneToMany(mappedBy = "municipio", cascade = CascadeType.ALL)
    private List<ZonaRisco> zonasRisco;

    @OneToMany(mappedBy = "municipio", cascade = CascadeType.ALL)
    private List<UsuarioMunicipio> usuarioMunicipios;
}