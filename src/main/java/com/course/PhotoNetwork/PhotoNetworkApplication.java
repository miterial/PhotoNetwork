package com.course.PhotoNetwork;

import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.RoleModel;
import com.course.PhotoNetwork.repository.CategoryRepository;
import com.course.PhotoNetwork.repository.RoleRepository;
import com.course.PhotoNetwork.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PhotoNetworkApplication {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ServiceRepository serviceRepository;

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
			if(categoryRepository.findAll().isEmpty()) {
				CategoryModel categoryModel = new CategoryModel();
				categoryModel.setName("cat1");
				categoryRepository.save(categoryModel);
			}
		};
	}
}
