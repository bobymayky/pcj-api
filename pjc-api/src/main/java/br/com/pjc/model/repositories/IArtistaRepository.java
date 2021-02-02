package br.com.pjc.model.repositories;

import java.util.List;

import br.com.pjc.model.entities.Artista;
import br.com.pjc.persistences.exceptions.PersistenceException;

public interface IArtistaRepository {

	public void incluirArtista(String nome) throws PersistenceException;

	public void atualizarArtista(Integer id, String nome) throws PersistenceException;

	public List<Artista> listarArtista(String nome);

	public Artista buscarArtistaPorId(Integer id);

}
