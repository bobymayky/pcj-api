package br.com.pjc.model.repositories;

import java.util.List;

import br.com.pjc.model.entities.Album;
import br.com.pjc.model.entities.AlbumImagem;
import br.com.pjc.model.entities.Artista;
import br.com.pjc.persistences.exceptions.PersistenceException;


public interface IAlbumRepository {

	public void incluirAlbum(Artista artista, String nome) throws PersistenceException;

	public void atualizarAlbum(Integer id, String nome) throws PersistenceException;

	public List<Album> listarAlbumPorArtista(Artista artista, String nome);

	public Album buscarAlbumPorId(Integer id);

	public void incluirAlbumImagem(Album album, String urlImagem) throws PersistenceException;

	public List<Album> listarAlbuns(String nome, Integer quantidade, Integer pagina);

	public List<AlbumImagem> listarImageAlbumPorAlbum(Album album);

	public AlbumImagem buscarImageAlbumPorId(Integer id);



}
