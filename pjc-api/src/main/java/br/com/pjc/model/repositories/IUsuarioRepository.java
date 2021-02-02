package br.com.pjc.model.repositories;

import java.sql.Timestamp;

import javax.persistence.NoResultException;

import br.com.pjc.model.entities.Usuario;
import br.com.pjc.model.entities.UsuarioSessao;
import br.com.pjc.persistences.exceptions.PersistenceException;

public interface IUsuarioRepository  {

	public void registrarSessao(Usuario usuario, String ipOrigem, Timestamp dataCriacao, Timestamp dataExpiracao, String token)throws PersistenceException;


	public Usuario buscarUsuarioPorLogin(String login);


	public UsuarioSessao buscarSessao(String token) 	throws PersistenceException, NoResultException;





}
