package br.com.pjc.model.entities;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_usuario_sessao")
public class UsuarioSessao {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "usuario_sessao_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;


	@Column(name = "usuario_sessao_ip", length = 50, nullable = false)
	@NotNull
	private String ip;

	@Column(name = "usuario_sessao_data_criacao", length = 50, nullable = false)
	@NotNull
	private Timestamp dataCriacao;

	@Column(name = "usuario_sessao_data_expiracao", length = 50, nullable = false)
	@NotNull
	private Timestamp dataExpiracao;

	@Column(name = "usuario_sessao_ativa", length = 50, nullable = false)
	@NotNull
	private boolean ativa;

	@Column(name = "usuario_sessao_token", length = 50, nullable = false)
	@NotNull
	private String token;
	


	public UsuarioSessao() {
	}

	public UsuarioSessao(Integer id) {
		super();
		setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Timestamp getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(Timestamp dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}