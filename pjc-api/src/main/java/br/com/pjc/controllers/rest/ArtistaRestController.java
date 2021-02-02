package br.com.pjc.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pjc.controllers.services.ArtistaService;
import br.com.pjc.controllers.services.exceptions.ServiceException;
import br.com.pjc.model.entities.Artista;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/artista")
public class ArtistaRestController {
	
	@Autowired
	private ArtistaService artistaService;
	
	public ArtistaService getArtistaService() {
		return artistaService;
	}
	
	@ApiOperation("Incluir Artista.")
	@RequestMapping(value="/incluir",method = RequestMethod.POST)
	public @ResponseBody void incluirArtista( @RequestBody String nome  ) {
				try {
					getArtistaService().incluirArtista(nome);
				} catch (ServiceException e) {
					e.printStackTrace();
				}
	}
	
	@ApiOperation("atualizar Artista.")
	@RequestMapping(value="/atualizar/{id}",method = RequestMethod.PUT)
	public void atualizarArtista(
			@PathVariable(value="id") Integer id,
			@RequestBody(required=true) String nome) {
		try {
			getArtistaService().atualizarArtista(id, nome);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	
	@ApiOperation("Listar artistas.")
	@RequestMapping(path = "/listar/{nome}", method = RequestMethod.GET)
	public @ResponseBody List<Artista> listarArtista( @PathVariable(value="nome") String nome) {
			try {
				return getArtistaService().listarArtista(nome);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			return null;
	}
	
}
