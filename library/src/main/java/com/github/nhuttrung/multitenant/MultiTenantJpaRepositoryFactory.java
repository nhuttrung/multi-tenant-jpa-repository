package com.github.nhuttrung.multitenant;

import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

class MultiTenantJpaRepositoryFactory extends JpaRepositoryFactory {
  MultiTenantJpaRepositoryFactory(EntityManager entityManager) {
    super(new MultiTenantEntityManager(entityManager));
  }
}
