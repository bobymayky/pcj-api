package br.com.pjc.controllers.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.pjc.controllers.services.AlbumService;
import br.com.pjc.controllers.services.exceptions.ServiceException;
import br.com.pjc.model.entities.Album;
import br.com.pjc.model.entities.Artista;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/album")
public class AlbumRestController {
	
	@Autowired
	private AlbumService albumService;
	

	public AlbumService getAlbumService() {
		return albumService;
	}
	
	
	@ApiOperation("Incluir Album.")
	@RequestMapping(value="/incluir",method = RequestMethod.POST)
	public @ResponseBody void incluirAlbum( 
					 @RequestParam(value="artistaId") Integer artistaId,
					 @RequestParam(value="nome") String nome ) {
				try {
					Artista artista = new Artista(artistaId);
					getAlbumService().incluirAlbum(artista, nome);
				} catch (ServiceException e) {
					e.printStackTrace();
				}
	}
	
	
	@ApiOperation("Listar albuns.")
	@RequestMapping(path = "/listar", method = RequestMethod.GET)
	public @ResponseBody List<Album> listarAlbum(
			                            @RequestParam(value="nome") String nome,
			                            @RequestParam(value="quantidade") Integer quantidade,
			                            @RequestParam(value="pagina") Integer pagina) {
			try {
				return getAlbumService().listarAlbuns(nome, quantidade, pagina);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			return null;
	}
		

}
