package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_NOTIFICACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notificacao")
    @SequenceGenerator(name = "seq_notificacao", sequenceName = "SEQ_NOTIFICACAO", allocationSize = 1)
    @Column(name = "ID_NOTIFICACAO")
    private Long id;

    @Column(name = "TP_NOTIFICACAO", nullable = false, length = 20)
    private String tipoNotificacao; // ALERTA, URGENTE, INFO

    @Column(name = "DS_TITULO", nullable = false, length = 200)
    private String titulo;

    @Column(name = "DS_MENSAGEM", length = 2000)
    private String mensagem;

    @Column(name = "ST_LIDA", length = 1)
    private String statusLida = "N";

    @Column(name = "DT_ENVIO")
    private LocalDateTime dataEnvio;

    @ManyToOne
    @JoinColumn(name = "ID_ALERTA", nullable = false)
    private Alerta alerta;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;
}