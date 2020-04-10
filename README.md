### Multitenancy
The term "software multitenancy" refers to a software architecture in which a single instance of 
software runs on a server and serves multiple tenants (Source: https://en.wikipedia.org/wiki/Multitenancy)

### Multi tenant JPA Repository
This library supports multi-tenant JPA repository. The repository will automatically filter data 
regarding to user's tenant.

### Demo
In this demo, we'll build a multi-tenant webapp to list all pets.   
```
  $ git clone https://github.com/nhuttrung/multi-tenant-jpa-repository.git
  cd multi-tenant-jpa-repository/
  ./gradlew demo:bootRun
```

- First sign-in by user1 and you will see pets belong to tenant 1 only.
- Then try sign-in by user2 to see the different result.  

### Usage
We will show how to build a multi tenant application in which tenants are using shared database 
and shared schema. 

- Add `@EnableJpaRepositories` to your Spring bott application:
```java
@SpringBootApplication
@EnableJpaRepositories(
  repositoryFactoryBeanClass= MultiTenantJpaRepositoryFactoryBean.class
)
public class DemoMultiTenantApplication {
  // ...
}
```

- Define filter by adding `@@FilterDef` and `@FilterDef` to your Entity:
```java
@Entity
@FilterDef(
    name = HasTenantId.FILTER_NAME,
    parameters = @ParamDef(name = HasTenantId.PARAM_NAME, type = "long"),
    defaultCondition = "(tenant_id = :" + HasTenantId.PARAM_NAME + ")"
)
@Filter(name = HasTenantId.FILTER_NAME)
public class Pet {
  // ...
}
```

- Implement `TenantIdProvider`
```java
@Component
public class MyTenantIdProvider implements TenantIdProvider {
  @Override
  public long getCurrentTenantId() {
    // return the tenant id of the current signed-in user
    // In real-world application, you can embed tenant id in JWT token.
    // Or you can look up the tenant id by username 
  } 
}
```

Behind the scenes, the multi-tenant JPA repository will determine tenant id of the current 
signed-in  user and add a where clause to SQL statement as below:
```
  WHERE (tenant_id = :tenantId) 
```

Therefore the user can see the data of his/her tenant only.
