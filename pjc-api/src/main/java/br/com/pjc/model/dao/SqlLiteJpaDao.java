package br.com.pjc.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import org.springframework.stereotype.Component;


@Component
public class SqlLiteJpaDao {


	@PersistenceContext(unitName = "sqlLitePU")
	private EntityManager entityManager;

	/**
	 * @return Entity manager.
	 */
	public EntityManager getEntityManager(){
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	 
	public int execute( JdbcQuery query ) throws PersistenceException, TransactionRequiredException{
		Query wrappedQuery = getEntityManager().createNativeQuery(query.getQueryString());
		for( String parIdx : query.getParameters().keySet() ){
			wrappedQuery.setParameter( parIdx, query.getParameters().get(parIdx) );
		}
		if( getEntityManager().isOpen() ){
			return wrappedQuery.executeUpdate();
		} else {
			throw new TransactionRequiredException();
		}
	}
	
	public List<?> getResultList(JdbcQuery query) throws PersistenceException, TransactionRequiredException{
		Query wrappedQuery = getEntityManager().createNativeQuery(query.getQueryString());
		for( String parIdx : query.getParameters().keySet() ){
			wrappedQuery.setParameter( parIdx, query.getParameters().get(parIdx) );
		}
		if( query.getMaxResults() != null && query.getMaxResults() > 0 ) {
			wrappedQuery.setMaxResults( query.getMaxResults() );
		}
		if( query.getFirstResult() != null ) {
			wrappedQuery.setFirstResult( query.getFirstResult() );
		}
		EntityManager entityManager = getEntityManager();
		if( entityManager.isOpen() ){
			return wrappedQuery.getResultList();
		} else {
			throw new TransactionRequiredException();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(JdbcQuery query) throws PersistenceException, NoResultException, NonUniqueResultException, TransactionRequiredException{
		Query wrappedQuery = getEntityManager().createNativeQuery(query.getQueryString());
		for( String parIdx : query.getParameters().keySet() ){
			wrappedQuery.setParameter( parIdx, query.getParameters().get(parIdx) );
		}
		if( getEntityManager().isOpen() ){
			return (T) wrappedQuery.getSingleResult();
		} else {
			throw new TransactionRequiredException();
		}			
	}
}



