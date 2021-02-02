package br.com.pjc.model.repositories.impl;

import java.sql.Timestamp;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pjc.model.dao.JdbcQuery;
import br.com.pjc.model.dao.SqlLiteJpaDao;
import br.com.pjc.model.entities.Usuario;
import br.com.pjc.model.entities.UsuarioSessao;
import br.com.pjc.model.repositories.IUsuarioRepository;
import br.com.pjc.persistences.exceptions.PersistenceException;
import util.CastClassUtil;
import util.ValidationUtil;


@Repository
@Transactional("sqlLiteTransactionManager")
public class UsuarioRepository implements IUsuarioRepository{

	@Autowired
	private SqlLiteJpaDao dao;
	
	private SqlLiteJpaDao getDao() {
		return dao;
	}	



	@Override
	public Usuario buscarUsuarioPorLogin(String login) {
		String queryString = "	SELECT "
						   + "  		usuario_id, "
						   + "          usuario_login, "
						   + "          usuario_nome_completo, "
						   + "          usuario_senha"
						   + "  FROM tb_usuario"
						   + " where usuario_login = :login";
		JdbcQuery query = new JdbcQuery(queryString);
		query.getParameters().put("login", login);
		Object[] resultado = getDao().getSingleResult(query);
		Usuario retorno = new Usuario();
		if(!ValidationUtil.isEmpty(resultado)) {
			retorno.setId(CastClassUtil.toInteger(resultado[0]));
			retorno.setLogin(CastClassUtil.toString(resultado[1]));
			retorno.setNome(CastClassUtil.toString(resultado[2]));
			retorno.setSenha(CastClassUtil.toString(resultado[3]));
		}
		return retorno;
	}
	
	
	@Override
	public void registrarSessao( Usuario usuario, String ipOrigem, Timestamp dataCriacao, Timestamp dataExpiracao, String token ) throws PersistenceException {
		String queryString = " INSERT INTO tb_usuario_sessao ( "
				           + "              usuario_id,"
				           + "              usuario_sessao_ip,"
				           + "              usuario_sessao_data_criacao, "
				           + "              usuario_sessao_data_expiracao, "
				           + "              usuario_sessao_ativa,"
				           + "              usuario_sessao_token "
				           + "  )  VALUES ( "
				           + "             :usuarioId, "
				           + "             :ipOrigem,"
				           + "             :dataCriacao,"
				           + "             :dataExpiracao, "
				           + "             :ativa,"
				           + "             :token"
				           + " ) ";
				        JdbcQuery query = new JdbcQuery(queryString);
				   		query.getParameters().put("usuarioId", usuario.getId());
				   		query.getParameters().put("ipOrigem", ipOrigem);
				   		query.getParameters().put("dataCriacao", dataCriacao);
				   		query.getParameters().put("dataExpiracao", dataExpiracao);
				   		query.getParameters().put("ativa", true);
				   		query.getParameters().put("token", token);
				   		getDao().execute(query);
	}
	
	@Override
	public UsuarioSessao buscarSessao( String token ) throws PersistenceException, NoResultException{
		String queryString = "	SELECT "
						   + "        usuario_sessao_id, "
						   + "        usuario_id, "
						   + "        usuario_sessao_ip,"
						   + "        usuario_sessao_data_criacao, "
						   + "        usuario_sessao_data_expiracao,"
						   + "        usuario_sessao_ativa, "
						   + "        usuario_sessao_token "
						   + "   FROM "
						   + "        tb_usuario_sessao "
						   + " WHERE  usuario_sessao_token = :token";
		JdbcQuery query = new JdbcQuery(queryString);
		query.getParameters().put("token", token);
		Object[] resultado = getDao().getSingleResult(query);
		UsuarioSessao retorno = new UsuarioSessao();
		if(!ValidationUtil.isEmpty(resultado)) {
			retorno.setId(CastClassUtil.toInteger(resultado[0]));
			retorno.setUsuario(new Usuario(CastClassUtil.toInteger(resultado[1])));
			retorno.setIp(CastClassUtil.toString(resultado[2]));
			retorno.setDataCriacao(CastClassUtil.toTimestamp(resultado[3]));
			retorno.setDataExpiracao(CastClassUtil.toTimestamp(resultado[4]));
			retorno.setAtiva(CastClassUtil.toBoolean(resultado[5]));
			retorno.setToken(CastClassUtil.toString(resultado[6]));
		}
         return retorno;
	}
		
	
}