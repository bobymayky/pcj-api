package br.com.pjc.controllers.services;

import java.security.Key;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.pjc.model.entities.Usuario;
import br.com.pjc.model.entities.UsuarioSessao;
import br.com.pjc.model.repositories.IUsuarioRepository;
import br.com.pjc.model.valuesobjects.AccessTokenResponse;
import br.com.pjc.persistences.exceptions.PersistenceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import util.DateTimeUtil;
import util.ValidationUtil;


@Service
public class AutenticacaoService {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	

	@Value("${jwt.signkey}")
	private String chaveAssinaturaJwt;
		
	private IUsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}
	
	/**
	 * @return Serviço de log do servidor.
	 */
	private Logger getLogger() {
		return logger;
	}
	
	/**
	 * @return Chave de assinatura do JWT.
	 */
	private Key getChaveAssinaturaJwt() {
		return new SecretKeySpec( chaveAssinaturaJwt.getBytes(), 0, chaveAssinaturaJwt.getBytes().length, "DES");
	}

	
	public AccessTokenResponse autenticarUsuario( String clientHost, String login, String senha )  {
		try {
			Usuario usuario = getUsuarioRepository().buscarUsuarioPorLogin(login);
			if(!ValidationUtil.isEmpty(usuario)) {
				if (usuario.getSenha().equals(senha)) {
					String tokenSessao = UUID.randomUUID().toString() + DateTimeUtil.getCurrentDateTime().getTime();
					Timestamp dataRequisicao = DateTimeUtil.getCurrentDateTime();
					Timestamp dataExpiracao = new Timestamp( DateTimeUtil.dateIncrement( dataRequisicao, 5, DateTimeUtil.MINUTE ).getTime() );

					getUsuarioRepository().registrarSessao(usuario, clientHost, dataRequisicao, dataExpiracao, tokenSessao);
					AccessTokenResponse response = new AccessTokenResponse();
					response.setAccessToken(Jwts.builder().setSubject(login).setIssuer(clientHost).setIssuedAt( dataRequisicao ).setExpiration(dataExpiracao).setId(tokenSessao).signWith(SignatureAlgorithm.HS512, getChaveAssinaturaJwt()).compact());	
					return response;
				} else {
					//	throw new SenhaIncorretaException();
				}
			} else {
				//	throw new SenhaIncorretaException();
			}	
		} catch (PersistenceException e) {
			//	throw new ErroAutenticacaoException(e);
		} catch (NoResultException e) {
			//throw new UsuarioNaoEncontradoException();
		}

		return null;
	}
	
	

	
	
	public UsuarioSessao buscarSessao( String clientHost, String token ) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(getChaveAssinaturaJwt()).parseClaimsJws(token);
			String tokenSessao = claims.getBody().getId();
			UsuarioSessao sessao = getUsuarioRepository().buscarSessao(tokenSessao);
			if( !sessao.isAtiva() ){
				//throw new SessaoExpiradaException();
			}
			if( sessao.getDataExpiracao().after( DateTimeUtil.getCurrentDateTime() ) ){
				return sessao;
			} else {
			//	throw new SessaoExpiradaException();
			}
		} catch (PersistenceException e) {
			//throw new ErroAutenticacaoException();
		} catch (NoResultException e) {
			//throw new SessaoInvalidaException();
		}
		
		return null;
	} 	
	

	
}