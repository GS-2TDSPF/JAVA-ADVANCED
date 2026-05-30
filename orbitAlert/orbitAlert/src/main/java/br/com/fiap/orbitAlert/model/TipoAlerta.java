package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "TB_TIPO_ALERTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoAlerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_alerta")
    @SequenceGenerator(name = "seq_tipo_alerta", sequenceName = "SEQ_TIPO_ALERTA", allocationSize = 1)
    @Column(name = "ID_TIPO_ALERTA")
    private Long id;

    @Column(name = "NM_TIPO", nullable = false, unique = true, length = 100)
    private String nome; // DESLIZAMENTO, ENCHENTE, SECA, EROSAO

    @Column(name = "DS_DESCRICAO", length = 500)
    private String descricao;

    @OneToMany(mappedBy = "tipoAlerta", cascade = CascadeType.ALL)
    private List<Alerta> alertas;
}