package fr.insee.formation.hibernate5.dao;

public interface GenericDao<T, ID> {

	void detach(T t);

	T find(ID id);

	T merge(T arg0);

	void persist(T arg0);

	void refresh(T arg0);

	void remove(T arg0);

}