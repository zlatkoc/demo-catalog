package com.nfcsb.demo.catalog;

/**
 *
 */

import com.nfcsb.demo.catalog.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServer.class)
@WebAppConfiguration
public class UserRepositoryTest {

	@Autowired
	UserRepository repository;

	@Autowired
	GroupRepository groups;

	@Before
	public void startUp() {

		groups.deleteAll();
		repository.deleteAll();
	}

	@Test
	public void createAndListUsers() {

		Iterable<User> list = repository.findAll();
		assertFalse(list.iterator().hasNext());

		User user = new User("Hello!");
		repository.save(user);

		list = repository.findAll();
		assertTrue(list.iterator().hasNext());
		User compare = list.iterator().next();
		assertNotNull(compare.getId());
		assertEquals("Hello!", compare.getName());
	}

	@Test
	public void findByNameTest() {


		repository.save(new User("Hektor"));
		repository.save(new User("Helena"));

		List<User> found = repository.findByName("Hektor");
		assertEquals(1, found.size());
		assertEquals("Hektor", found.get(0).getName());
	}
}
