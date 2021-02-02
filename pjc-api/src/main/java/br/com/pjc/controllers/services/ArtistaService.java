package br.com.pjc.controllers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pjc.controllers.services.exceptions.ServiceException;
import br.com.pjc.model.entities.Artista;
import br.com.pjc.model.repositories.IArtistaRepository;
import br.com.pjc.persistences.exceptions.PersistenceException;




@Service
public class ArtistaService {
	
	
	@Autowired
	private IArtistaRepository artistaRepository;
	
	public IArtistaRepository getArtistaRepository() {
		return artistaRepository;
	}
	
	public void incluirArtista( String nome ) throws ServiceException{
		try {
			getArtistaRepository().incluirArtista(nome);
		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
	}
	
	
	public void atualizarArtista(Integer id, String nome) throws ServiceException{
		try {
			getArtistaRepository().atualizarArtista(id, nome);
		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
	}
	
	
	public  List<Artista> listarArtista(String nome) throws ServiceException{
		return getArtistaRepository().listarArtista(nome);
	}
	
	public  Artista buscarArtistaPorId(Integer id) throws ServiceException{
		return getArtistaRepository().buscarArtistaPorId(id);
	}


}
