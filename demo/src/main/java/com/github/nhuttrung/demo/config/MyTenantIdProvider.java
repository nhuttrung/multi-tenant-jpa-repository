package com.github.nhuttrung.demo.config;

import com.github.nhuttrung.multitenant.TenantIdProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class MyTenantIdProvider implements TenantIdProvider {
  @Override
  public long getCurrentTenantId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Object principal = auth.getPrincipal();
    // In real-world application, you should have other mechanism to map the Principal to Tenant Id
    // For example, you can embed tenant id in the JWT token.
    // Or you can look up the tenant id by username

    // In this demo, we hard-code the tenant id
    if (principal instanceof UserDetails) {
      String username = ((UserDetails)auth.getPrincipal()).getUsername();
      System.out.println("username: " + username);
      if ("user1".equalsIgnoreCase(username)){
        return 1;
      } else {
        return 2;
      }
    }

    return 0;
  }
}
