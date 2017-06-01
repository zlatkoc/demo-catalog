package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.CatalogGroup;
import com.nfcsb.demo.catalog.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServer.class)
@WebAppConfiguration
public class GroupRepositoryTest {

	@Autowired
	UserRepository users;

	@Autowired
	GroupRepository groups;

	@Before
	public void startUp() {
		groups.deleteAll();
		users.deleteAll();
	}
	@Test
	public void createGroupTest() {

		CatalogGroup group = groups.save(new CatalogGroup("A-team"));

		User adam = users.save(new User("Adam"));

		group.addMember(adam);
		groups.save(group);

		//

		CatalogGroup compare = groups.findOne(group.getId());

		assertEquals(1, compare.getMembers().size());
		assertEquals("Adam", compare.getMembers().iterator().next().getName());
/*
		List<User> members = users.listUsersInGroup(compare.getId());
		assertEquals(1, members.size());*/
	}
}
