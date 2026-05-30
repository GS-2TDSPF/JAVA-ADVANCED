package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USUARIO_MUNICIPIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioMunicipio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario_municipio")
    @SequenceGenerator(name = "seq_usuario_municipio", sequenceName = "SEQ_USUARIO_MUNICIPIO", allocationSize = 1)
    @Column(name = "ID_USUARIO_MUNICIPIO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_MUNICIPIO", nullable = false)
    private Municipio municipio;

    @Column(name = "DT_VINCULO")
    private LocalDateTime dataVinculo;
}