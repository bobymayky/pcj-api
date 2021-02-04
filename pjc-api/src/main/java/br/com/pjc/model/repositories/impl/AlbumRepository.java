package br.com.pjc.model.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pjc.model.dao.JdbcQuery;
import br.com.pjc.model.dao.SqlLiteJpaDao;
import br.com.pjc.model.entities.Album;
import br.com.pjc.model.entities.AlbumImagem;
import br.com.pjc.model.entities.Artista;
import br.com.pjc.model.repositories.IAlbumRepository;
import br.com.pjc.persistences.exceptions.PersistenceException;
import util.CastClassUtil;
import util.ValidationUtil;



@Repository
@Transactional("sqlLiteTransactionManager")
public class AlbumRepository implements IAlbumRepository{

	@Autowired
	private SqlLiteJpaDao dao;
	
	private SqlLiteJpaDao getDao() {
		return dao;
	}	
	
	
	@Override
	public void incluirAlbum( Artista artista, String nome ) throws PersistenceException {
		String queryString = " INSERT INTO tb_album ("
				           + "             album_nome, "
				           + "             artista_id "
				           + "  ) VALUES ( "
				           + "      :nome, "
				           + "      :artistaId "
				           + " )";
				        JdbcQuery query = new JdbcQuery(queryString);
				   		query.getParameters().put("nome", nome);
				   		query.getParameters().put("artistaId", artista.getId());
				   		getDao().execute(query);
	}
	
	
	@Override
	public void atualizarAlbum(Integer id, String nome ) throws PersistenceException {
		String queryString = " UPDATE tb_album "
				           + "  SET album_nome = :nome "
				           + " WHERE album_id = :albumId";
				        JdbcQuery query = new JdbcQuery(queryString);
				   		query.getParameters().put("AlbumId", id);
				   		query.getParameters().put("nome", nome);
				   		getDao().execute(query);
	}
	
	
	@Override
	public List<Album> listarAlbuns( String nome, Integer quantidade, Integer pagina) {
		String queryString = "	SELECT "
						   + "        ab.album_id, "
				           + "        ab.album_nome, "
				           + "        ar.artista_id, "
				           + "        ar.artista_nome "
				           + "  FROM "
				           + "       tb_album ab "
				           + "  INNER JOIN tb_artista ar on ar.artista_id = ab.artista_id "
				           +(!ValidationUtil.isEmpty(nome) ?  "  WHERE ar.artista_nome like  '%"+nome+"%' OR  ab.album_nome like  '%"+nome+"%'" : "" ) 			           
				           + " LIMIT  " + quantidade
				           + " OFFSET " + pagina;
		JdbcQuery query = new JdbcQuery(queryString);
		List<Object[]> resultado = (List<Object[]>) getDao().getResultList(query);
		List<Album> retorno = new ArrayList<Album>();
		for(Object registro[] : resultado) {
			Album album = new Album();
			album.setId(CastClassUtil.toInteger(registro[0]));
			album.setNome(CastClassUtil.toString(registro[1]));
			album.setArtista(new Artista(CastClassUtil.toInteger(registro[2])));
			album.getArtista().setNome(CastClassUtil.toString(registro[3]));
			retorno.add(album);
		}
		return retorno;
	}
	
	
	@Override
	public List<AlbumImagem> listarImageAlbumPorAlbum( Album album) {
		String queryString = "	SELECT "
						   + "         ai.album_imagem_id, "
						   + "         a.album_id, "
						   + "         a.album_nome, "
						   + "         ai.album_imagem_url_foto "
						   + "  FROM "
						   + "       tb_album_imagem ai "
						   + "  INNER JOIN tb_album a on a.album_id = ai.album_id "
						   + "  WHERE "
						   + "        a.album_id = :albumId "
					       + "  ORDER BY album_imagem_id ASC ";
		JdbcQuery query = new JdbcQuery(queryString);
		query.getParameters().put("albumId", album.getId());
		List<Object[]> resultado = (List<Object[]>) getDao().getResultList(query);
		List<AlbumImagem> retorno = new ArrayList<AlbumImagem>();
		for(Object registro[] : resultado) {
			AlbumImagem albumImage = new AlbumImagem();
			albumImage.setId(CastClassUtil.toInteger(registro[0]));
			albumImage.setAlbum(new Album(CastClassUtil.toInteger(registro[1])));
			albumImage.getAlbum().setNome(CastClassUtil.toString(registro[2]));
			albumImage.setUrlFoto(CastClassUtil.toString(registro[3]));
			retorno.add(albumImage);
		}
		return retorno;
	}
	
	
	
	@Override
	public AlbumImagem buscarImageAlbumPorId( Integer id) {
		String queryString = "	SELECT "
						   + "         ai.album_imagem_id, "
						   + "         a.album_id, "
						   + "         a.album_nome, "
						   + "         ai.album_imagem_url_foto "
						   + "  FROM "
						   + "       tb_album_imagem ai "
						   + "  INNER JOIN tb_album a on a.album_id = ai.album_id "
						   + "  WHERE "
						   + "        ai.album_imagem_id = :albumImageId "
					       + "  ORDER BY album_imagem_id ASC ";
		JdbcQuery query = new JdbcQuery(queryString);
		query.getParameters().put("albumImageId", id);
		List<Object[]> resultado = (List<Object[]>) getDao().getResultList(query);
		AlbumImagem albumImagem = new AlbumImagem();
		for(Object registro[] : resultado) {
			albumImagem.setId(CastClassUtil.toInteger(registro[0]));
			albumImagem.setAlbum(new Album(CastClassUtil.toInteger(registro[1])));
			albumImagem.getAlbum().setNome(CastClassUtil.toString(registro[2]));
			albumImagem.setUrlFoto(CastClassUtil.toString(registro[3]));
		}
		return albumImagem;
	}
	
	
	


	@Override
	public List<Album> listarAlbumPorArtista(Artista artista, String nome) {
		String queryString = "	SELECT "
						   + "        ab.album_id, "
				           + "        ab.album_nome, "
				           + "        ar.artista_id, "
				           + "        ar.artista_nome "
				           + "  FROM "
				           + "       tb_album ab "
				           + "  INNER JOIN tb_artista ar on ar.artista_id = ab.artista_id "
				  		   + " where artista_id = :artistaId";
		JdbcQuery query = new JdbcQuery(queryString);
		query.getParameters().put("artistaId", artista.getId());
		List<Object[]> resultado = (List<Object[]>) getDao().getResultList(query);
		List<Album> retorno = new ArrayList<Album>();
		for(Object registro[] : resultado) {
			Album album = new Album();
			album.setId(CastClassUtil.toInteger(registro[0]));
			album.setNome(CastClassUtil.toString(registro[1]));
			album.setArtista(new Artista(CastClassUtil.toInteger(registro[2])));
			album.getArtista().setNome(CastClassUtil.toString(registro[3]));
			retorno.add(album);
		}
		return retorno;
	}
	
	
	
	@Override
	public Album buscarAlbumPorId(Integer id) {
		String queryString = "	SELECT "
						   + " 		  album_id, "
				  		   + "        album_nome "
				  		   + "  FROM "
				  		   + "       tb_album "
				  		   + "  WHERE "
				  		   + "       album_id = :id ";
			JdbcQuery query = new JdbcQuery(queryString);
			query.getParameters().put("id", id);
			Object[] resultado = getDao().getSingleResult(query);
			Album album = new Album();
			if(!ValidationUtil.isEmpty(resultado)) {
				album.setId(CastClassUtil.toInteger(resultado[0]));
				album.setNome(CastClassUtil.toString(resultado[1]));
			}
			return album;
	}
	
	
	@Override
	public void incluirAlbumImagem( Album album, String urlImagem ) throws PersistenceException {
		String queryString = " INSERT INTO tb_album_imagem ( "
						   + "              album_id, "
						   + "              album_imagem_url_foto"
						   + "       ) VALUES ( "
						   + "           :albumId, "
						   + "           :url );";
				        JdbcQuery query = new JdbcQuery(queryString);
				        query.getParameters().put("albumId", album.getId());
				   		query.getParameters().put("url", urlImagem);
				   		getDao().execute(query);
	}
	
	
	

	
}