package br.com.pjc.model.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import util.validator.annotations.MD5Hash;

@Entity
@Table(name = "tb_usuario")
public class Usuario  {

	private static final long serialVersionUID = 1L;

		@Id
		@Column(name = "usuario_id", nullable = false)
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "usuario_nome_completo")
		private String nome;
		
		@Column(name = "usuario_login")
		private String login;	
		
		@Column(name = "usuario_senha")
		@NotNull
		@MD5Hash
		@JsonProperty(access = Access.WRITE_ONLY)
		private String senha;
		
		public Usuario() {
			
		}
		
		public Usuario( Integer id) {
			this.id = id;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}
	
	
}	
	