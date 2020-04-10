package com.github.nhuttrung.demo;

import com.github.nhuttrung.multitenant.MultiTenantJpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
		repositoryFactoryBeanClass= MultiTenantJpaRepositoryFactoryBean.class
)
public class DemoMultiTenantApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMultiTenantApplication.class, args);
	}

}
