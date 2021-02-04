package br.com.pjc.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tb_album")
public class Album  {

	private static final long serialVersionUID = 1L;

		@Id
		@Column(name = "album_id", nullable = false)
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "album_nome")
		private String nome;
		
		@ManyToOne
		@JoinColumn(name = "artista_id")
		private Artista artista;
		
		public Album() {
			
		}
		
		public Album(Integer id) {
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

		public Artista getArtista() {
			return artista;
		}

		public void setArtista(Artista artista) {
			this.artista = artista;
		}

}