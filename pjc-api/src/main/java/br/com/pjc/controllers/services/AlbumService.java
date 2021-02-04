package br.com.pjc.controllers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pjc.controllers.services.exceptions.ServiceException;
import br.com.pjc.model.entities.Album;
import br.com.pjc.model.entities.AlbumImagem;
import br.com.pjc.model.entities.Artista;
import br.com.pjc.model.repositories.IAlbumRepository;
import br.com.pjc.persistences.exceptions.PersistenceException;
import util.Pageset;

@Service
public class AlbumService {
	
	@Autowired
	private IAlbumRepository albumRepository;
	
	public IAlbumRepository getAlbumRepository() {
		return albumRepository;
	}
	
	
	public void incluirAlbum(Artista artista, String nome) throws ServiceException{
		try {
			getAlbumRepository().incluirAlbum(artista, nome);
		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
	}

	public void atualizarAlbum(Integer id, String nome) throws ServiceException{
		try {
			getAlbumRepository().atualizarAlbum(id, nome);
		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
	}
	
	
	public Pageset<Album> listarAlbuns(String nome, Integer quantidade, Integer pagina) throws ServiceException{
		return getAlbumRepository().listarAlbuns(nome, quantidade,  pagina);
	}
	
	public  List<Album> listarAlbumPorArtista(Artista artista, String nome) throws ServiceException{
		return getAlbumRepository().listarAlbumPorArtista(artista, nome);
	}
	
	
	public Album buscarAlbumPorId(Integer id) throws ServiceException{
		return getAlbumRepository().buscarAlbumPorId(id);
	}
	
	
	
	public void incluirImagemAlbum(Album album, String urlImagem) throws ServiceException {
		try {
			getAlbumRepository().incluirAlbumImagem(album, urlImagem);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean existeUrlCadastro(Album album, String urlImagem) throws ServiceException {
		try {
			return getAlbumRepository().existeUrlCadastro(album, urlImagem);
		} catch (PersistenceException e) {
			return false;
		}
	}
	
	
	public List<AlbumImagem> listarImageAlbumPorAlbum(Album album)throws ServiceException {
		return getAlbumRepository().listarImageAlbumPorAlbum(album);
	}
	
	
	public AlbumImagem buscarImageAlbumPorId(Integer id) {
		return getAlbumRepository().buscarImageAlbumPorId(id);
	}

}
