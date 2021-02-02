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
@Table(name = "tb_album_imagem")
public class AlbumImagem  {

	private static final long serialVersionUID = 1L;

		@Id
		@Column(name = "album_imagem_id", nullable = false)
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
				
		@ManyToOne
		@JoinColumn(name = "album_id")
		private Album album;
		
		@Column(name = "album_imagem_url_foto")
		private String urlFoto;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Album getAlbum() {
			return album;
		}

		public void setAlbum(Album album) {
			this.album = album;
		}

		public String getUrlFoto() {
			return urlFoto;
		}

		public void setUrlFoto(String urlFoto) {
			this.urlFoto = urlFoto;
		}	
		
}