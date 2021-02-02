package br.com.pjc.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_artista")
public class Artista  {

		@Id
		@Column(name = "artista_id", nullable = false)
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "artista_nome")
		private String nome;
		
		
		public Artista(){
			
		}
		
		public Artista(Integer id){
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
				
		
}

