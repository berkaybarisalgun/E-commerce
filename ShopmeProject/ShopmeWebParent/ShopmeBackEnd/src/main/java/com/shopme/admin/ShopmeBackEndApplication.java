package com.shopme.admin;

import com.shopme.admin.user.UserService;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan({"com.shopme.common.entity","com.shopme.admin.user"})
public class ShopmeBackEndApplication {

	@Autowired
	private UserService service;


	public static void main(String[] args) {
		SpringApplication.run(ShopmeBackEndApplication.class, args);
	}




}


