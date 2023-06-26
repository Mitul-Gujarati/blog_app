package com.blog.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.api.config.AppConstants;
import com.blog.api.entities.Role;
import com.blog.api.repositories.RoleRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class BlogAppBackendApplication implements CommandLineRunner {

	@Value("${spring.profiles.active}")
	private String environment;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppBackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {

			Role role = new Role();
			role.setRoleId(AppConstants.ROLE_ADMIN);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setRoleId(AppConstants.ROLE_NORMAL);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role, role1);

			List<Role> savedRoles = roleRepository.saveAll(roles);
			savedRoles.forEach(r -> {
				System.out.println(r.getName());
			});
			System.out.println("Current Active Environment: "+environment);

		} catch (Exception e) {
		}
	}
}
