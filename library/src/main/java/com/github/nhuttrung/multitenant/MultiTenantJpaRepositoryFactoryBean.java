package com.github.nhuttrung.multitenant;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class MultiTenantJpaRepositoryFactoryBean <T extends Repository<S, ID>, S, ID> extends
    JpaRepositoryFactoryBean<T, S, ID> {
  private static TenantIdProvider tenantIdProvider;
  static TenantIdProvider getTenantIdProvider() {
    return tenantIdProvider;
  }

  private EntityPathResolver entityPathResolver;
  private EscapeCharacter escapeCharacter = EscapeCharacter.DEFAULT;

  public MultiTenantJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
    super(repositoryInterface);
  }

  @Autowired
  @Override
  public void setEntityPathResolver(ObjectProvider<EntityPathResolver> resolver) {
    super.setEntityPathResolver(resolver);
    this.entityPathResolver = resolver.getIfAvailable(() -> SimpleEntityPathResolver.INSTANCE);
  }

  @Override
  public void setEscapeCharacter(char escapeCharacter) {
    super.setEscapeCharacter(escapeCharacter);

    this.escapeCharacter = EscapeCharacter.of(escapeCharacter);
  }

  protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
    JpaRepositoryFactory jpaRepositoryFactory = new MultiTenantJpaRepositoryFactory(entityManager);
    jpaRepositoryFactory.setEntityPathResolver(entityPathResolver);
    jpaRepositoryFactory.setEscapeCharacter(escapeCharacter);
    return jpaRepositoryFactory;
  }

  @Autowired
  private void setTenantIdProvider(TenantIdProvider tenantIdProvider) {
    this.tenantIdProvider = tenantIdProvider;
  }
}
