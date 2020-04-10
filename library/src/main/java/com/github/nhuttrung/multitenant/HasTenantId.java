package com.github.nhuttrung.multitenant;

public interface HasTenantId {
  String FILTER_NAME = "MULTI_TENANT_FILTER";
  String PARAM_NAME = "TENANT_ID";

  long getTenantId();
}
