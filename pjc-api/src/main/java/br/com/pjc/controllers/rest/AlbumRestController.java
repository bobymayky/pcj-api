package br.com.pjc.controllers.rest;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import br.com.pjc.minio.MinioAdapter;
import br.com.pjc.model.entities.Album;
import br.com.pjc.model.entities.AlbumImagem;
import br.com.pjc.model.entities.Artista;
import io.swagger.annotations.ApiOperation;
import util.Pageset;
import util.ValidationUtil;

@RestController
@RequestMapping("/api/album")
public class AlbumRestController {
	
	@Autowired
	MinioAdapter minioAdapter;
	 
	@Autowired
	private AlbumService albumService;
	
	public MinioAdapter getMinioAdapter() {
		return minioAdapter;
    }

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
	
	@ApiOperation("Buscar Album.")
	@RequestMapping(value="/buscar",method = RequestMethod.GET)
	public @ResponseBody Album buscarAlbum( @RequestParam(value="id") Integer id ) {
				try {
					return getAlbumService().buscarAlbumPorId(id);
				} catch (ServiceException e) {
					return null;
				}
	}
	
	
	@ApiOperation("Listar albuns.")
	@RequestMapping(path = "/listar", method = RequestMethod.GET)
	public @ResponseBody Pageset<Album> listarAlbum(
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
	
	
	
	@PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public void uploadImagemAlbum(
			@RequestParam(value="albumId") Integer albumId,
			@RequestPart(value = "imagens", required = false) List<MultipartFile> files) throws IOException {
		try {
			Album album = getAlbumService().buscarAlbumPorId(albumId);
			for(MultipartFile multiPartFile : files ){
				String urlImagem = album.getNome()+"/"+multiPartFile.getOriginalFilename();
				getMinioAdapter().uploadImagem(urlImagem, multiPartFile.getContentType(), multiPartFile.getBytes());
				getAlbumService().incluirImagemAlbum(album, urlImagem);
			}
		} catch (ServiceException e) {
			
		} 	
	}
	
	
	@RequestMapping(value="/visualizar/capa",method = RequestMethod.GET,produces="application/base64")
	public @ResponseBody String visualizarCapaAlbum( 
			@RequestParam(name="albumId",required=true) Integer albumId
		){
		try {
			boolean encontrouCapa = false;
			String titulo = "";
			byte[] data = null;
			for(AlbumImagem albumImage : getAlbumService().listarImageAlbumPorAlbum(new Album(albumId))) {
				if(!encontrouCapa) {
					data = getMinioAdapter().getImage(albumImage.getUrlFoto());
					encontrouCapa = (!ValidationUtil.isEmpty(data) ? true : false);
					titulo = ((encontrouCapa) ? albumImage.getAlbum().getNome() : "");
				}
			}
			
			if(encontrouCapa) {
				return Base64.getEncoder().encodeToString(data);
			} else {
				return Base64.getEncoder().encodeToString(data);
			}
		} catch (ServiceException e) {
			return null;
		}

	}
	
	
	
	@RequestMapping(path = "/visualizar/image",method = RequestMethod.GET,produces="application/base64")
	public @ResponseBody String visualizarImagem(
												@RequestParam(value="id") Integer albumImageId) throws IOException {
		AlbumImagem albumImage  = getAlbumService().buscarImageAlbumPorId(albumImageId);
		byte[] data = getMinioAdapter().getImage(albumImage.getUrlFoto());
		return Base64.getEncoder().encodeToString(data);
	}
	
	


	

	
}
