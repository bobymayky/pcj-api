package br.com.pjc.model.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pjc.model.dao.JdbcQuery;
import br.com.pjc.model.dao.SqlLiteJpaDao;
import br.com.pjc.model.entities.Artista;
import br.com.pjc.model.repositories.IArtistaRepository;
import br.com.pjc.persistences.exceptions.PersistenceException;
import util.CastClassUtil;
import util.ValidationUtil;

@Repository
@Transactional("sqlLiteTransactionManager")
public class ArtistaRepository implements IArtistaRepository{

	@Autowired
	private SqlLiteJpaDao dao;
	
	private SqlLiteJpaDao getDao() {
		return dao;
	}	

	
	
	@Override
	public void incluirArtista( String nome ) throws PersistenceException {
		String queryString = " INSERT INTO tb_artista ("
				           + "              artista_nome "
				           + "    ) VALUES ( "
				           + "      :nome )";
				        JdbcQuery query = new JdbcQuery(queryString);
				   		query.getParameters().put("nome", nome);
				   		getDao().execute(query);
	}
	
	
	@Override
	public void atualizarArtista(Integer id, String nome ) throws PersistenceException {
		String queryString = " UPDATE tb_artista "
				           + "  SET artista_nome = :nome "
				           + " WHERE artista_id = :artistaId";
				        JdbcQuery query = new JdbcQuery(queryString);
				   		query.getParameters().put("artistaId", id);
				   		query.getParameters().put("nome", nome);
				   		getDao().execute(query);
	}


	@Override
	public List<Artista> listarArtista(String nome) {
		String queryString = "	SELECT "
						   + " 		  artista_id, "
				  		   + "        artista_nome "
				  		   + "  FROM "
				  		   + "       tb_artista";
		JdbcQuery query = new JdbcQuery(queryString);
		//query.getParameters().put("nome", nome);
		List<Object[]> resultado = (List<Object[]>) getDao().getResultList(query);
		List<Artista> retorno = new ArrayList<Artista>();
		for(Object registro[] : resultado) {
			Artista artista = new Artista();
			artista.setId(CastClassUtil.toInteger(registro[0]));
			artista.setNome(CastClassUtil.toString(registro[1]));
			retorno.add(artista);
		}
		return retorno;
	}
	
	
	
	@Override
	public Artista buscarArtistaPorId(Integer id) {
		String queryString = "	SELECT "
						   + " 		  artista_id, "
				  		   + "        artista_nome "
				  		   + "  FROM "
				  		   + "       tb_artista "
				  		   + "  WHERE "
				  		   + "       artista_id = :id ";
			JdbcQuery query = new JdbcQuery(queryString);
			query.getParameters().put("id", id);
			Object[] resultado = getDao().getSingleResult(query);
			Artista artista = new Artista();
			if(!ValidationUtil.isEmpty(resultado)) {
				artista.setId(CastClassUtil.toInteger(resultado[0]));
				artista.setNome(CastClassUtil.toString(resultado[1]));
			}
			return artista;
	}
	
	
	
}