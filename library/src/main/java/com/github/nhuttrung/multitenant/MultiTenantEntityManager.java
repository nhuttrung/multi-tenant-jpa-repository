package com.github.nhuttrung.multitenant;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MultiTenantEntityManager extends EntityManagerWrapper {
  private EntityManagerFactory entityManagerFactory;

  MultiTenantEntityManager(EntityManager em) {
    super(em);

    entityManagerFactory = em.getEntityManagerFactory();
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey) {
    T t = super.find(entityClass, primaryKey);
    t = checkTenantId(t);

    return t;
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
    T t = super.find(entityClass, primaryKey, properties);
    t = checkTenantId(t);

    return t;
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
    T t = super.find(entityClass, primaryKey, lockMode);
    t = checkTenantId(t);

    return t;
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode,
      Map<String, Object> properties) {
    T t = super.find(entityClass, primaryKey, lockMode, properties);
    t = checkTenantId(t);

    return t;
  }

  @Override
  public <T> T getReference(Class<T> entityClass, Object primaryKey) {
    T t = super.getReference(entityClass, primaryKey);
    t = checkTenantId(t);

    return t;
  }

  @Override
  public Query createQuery(String qlString) {
    setFilterParameters();

    return super.createQuery(qlString);
  }

  @Override
  public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
    setFilterParameters();

    return super.createQuery(criteriaQuery);
  }

  @Override
  public Query createQuery(CriteriaUpdate updateQuery) {
    setFilterParameters();

    return super.createQuery(updateQuery);
  }

  @Override
  public Query createQuery(CriteriaDelete deleteQuery) {
    setFilterParameters();

    return super.createQuery(deleteQuery);
  }

  @Override
  public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
    setFilterParameters();

    return super.createQuery(qlString, resultClass);
  }

  @Override
  public Query createNamedQuery(String name) {
    setFilterParameters();

    return super.createNamedQuery(name);
  }

  @Override
  public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
    setFilterParameters();

    return super.createNamedQuery(name, resultClass);
  }

  @Override
  public Query createNativeQuery(String sqlString) {
    setFilterParameters();

    return super.createNativeQuery(sqlString);
  }

  @Override
  public Query createNativeQuery(String sqlString, Class resultClass) {
    setFilterParameters();

    return super.createNativeQuery(sqlString, resultClass);
  }

  @Override
  public Query createNativeQuery(String sqlString, String resultSetMapping) {
    setFilterParameters();

    return super.createNativeQuery(sqlString, resultSetMapping);
  }

  @Override
  public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
    // TODO
    setFilterParameters();

    return super.createNamedStoredProcedureQuery(name);
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
    // TODO
    setFilterParameters();

    return super.createStoredProcedureQuery(procedureName);
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName,
      Class... resultClasses) {
    // TODO
    setFilterParameters();

    return super.createStoredProcedureQuery(procedureName, resultClasses);
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName,
      String... resultSetMappings) {
    // TODO
    setFilterParameters();

    return super.createStoredProcedureQuery(procedureName, resultSetMappings);
  }

  private void setFilterParameters(){
    long tenantId = MultiTenantJpaRepositoryFactoryBean.getTenantIdProvider().getCurrentTenantId();
    if (tenantId < 0) {
      return;
    }

    Session session = getCurrentSession();
    try {
      Filter filter = session.enableFilter(HasTenantId.FILTER_NAME);
      filter.setParameter(HasTenantId.PARAM_NAME, tenantId);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  private Session getCurrentSession(){
    Session session;

    // 1. Try to get session
    session = (Session) em.getDelegate();
    if (session.isOpen()){
      return session;
    }

    // 2. Try to get session
    SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    try{
      session = sessionFactory.getCurrentSession();
    }catch (HibernateException e){
      session = sessionFactory.openSession();
    }

    return session;
  }

  private <T> T checkTenantId(T t) {
    long tenantId = MultiTenantJpaRepositoryFactoryBean.getTenantIdProvider().getCurrentTenantId();
    if (t instanceof HasTenantId){
      if (((HasTenantId) t).getTenantId() != tenantId){
        return null;
      }
    }

    return t;
  }
}
