package com.github.nhuttrung.demo.data;

import com.github.nhuttrung.multitenant.HasTenantId;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "pet")
@FilterDef(
    name = HasTenantId.FILTER_NAME,
    parameters = @ParamDef(name = HasTenantId.PARAM_NAME, type = "long"),
    defaultCondition = "(tenant_id = :" + HasTenantId.PARAM_NAME + ")"
)
@Filter(name = HasTenantId.FILTER_NAME)
public class Pet {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "tenant_id", nullable = false)
  private long tenantId;

  public Pet(){
  }
  public Pet(String name, long tenantId){
    this.name = name;
    this.tenantId = tenantId;
  }

  // Getters and Setters
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
