package br.com.fiap.orbitAlert.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "NM_USUARIO", nullable = false, length = 150)
    private String nome;

    @Column(name = "DS_EMAIL", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "DS_SENHA", nullable = false, length = 255)
    private String senha;

    @Column(name = "TP_PERFIL", nullable = false, length = 20)
    private String tipoPerfil; // GESTOR, ADMIN, OPERADOR

    @Column(name = "ST_ATIVO", length = 1)
    private String statusAtivo = "S";

    @Column(name = "DT_CADASTRO")
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Notificacao> notificacoes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioMunicipio> usuarioMunicipios;
}