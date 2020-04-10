package com.github.nhuttrung.multitenant;

public interface TenantIdProvider {
  long getCurrentTenantId();
}
