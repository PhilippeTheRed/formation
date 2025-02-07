package fr.insee.formation.hibernate5.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractGenericDao <T,ID> implements GenericDao<T, ID>{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Class<T> type;
	
	public AbstractGenericDao(Class<T> type) {
		type = type;
	}
	
	/* (non-Javadoc)
	 * @see fr.insee.formation.hibernate5.dao.GenericDao#detach(T)
	 */
	@Override
	public void detach(T t) {
		entityManager.detach(t);
	}

	/* (non-Javadoc)
	 * @see fr.insee.formation.hibernate5.dao.GenericDao#find(java.lang.Class, ID)
	 */
	@Override
	public T find(ID id) {
		return entityManager.find(type, id);
	}

	/* (non-Javadoc)
	 * @see fr.insee.formation.hibernate5.dao.GenericDao#merge(T)
	 */
	@Override
	public T merge(T arg0) {
		return entityManager.merge(arg0);
	}

	/* (non-Javadoc)
	 * @see fr.insee.formation.hibernate5.dao.GenericDao#persist(T)
	 */
	@Override
	public void persist(T arg0) {
		entityManager.persist(arg0);
	}

	/* (non-Javadoc)
	 * @see fr.insee.formation.hibernate5.dao.GenericDao#refresh(T)
	 */
	@Override
	public void refresh(T arg0) {
		entityManager.refresh(arg0);
	}

	/* (non-Javadoc)
	 * @see fr.insee.formation.hibernate5.dao.GenericDao#remove(T)
	 */
	@Override
	public void remove(T arg0) {
		entityManager.remove(arg0);
	}

}
