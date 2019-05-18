package com.course.PhotoSocial;

import com.course.PhotoSocial.model.RoleModel;
import com.course.PhotoSocial.repository.RoleRepository;
import com.course.PhotoSocial.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PhotoNetworkApplication {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(PhotoNetworkApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext context) {
		return args -> {
			if(roleRepository.findAll().isEmpty()) {
				RoleModel role = new RoleModel();
				role.setRolename("USER");
				roleRepository.save(role);

				role = new RoleModel();
				role.setRolename("MASTER");
				roleRepository.save(role);

				role = new RoleModel();
				role.setRolename("ADMIN");
				roleRepository.save(role);
			}
		};
	}
}
