package com.desafiojavapitang.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class UserTests {

	@Test
	public void genreShouldHaveCorrectStructure() {
	
		UserEntity entity = new UserEntity();
		entity.setId(1L);
		entity.setFirstName("Bob");
		entity.setLastName("Falcon");
		entity.setEmail("bob@gmail.com");
		entity.setBirthday(new Date());
		entity.setLogin("bob_king3242");
		entity.setPassword("123456");
	
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getFirstName());
		Assertions.assertNotNull(entity.getLastName());
		Assertions.assertNotNull(entity.getEmail());
		Assertions.assertNotNull(entity.getBirthday());
		Assertions.assertNotNull(entity.getLogin());
		Assertions.assertNotNull(entity.getPassword());
	}
}
