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

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
		User eve = users.save(new User("Eve"));
		User john = users.save(new User("John"));
		User lebowski = users.save(new User("The Dude"));

		group.addMember(adam);
		group.addMember(lebowski);
		groups.save(group);

		//

		CatalogGroup compare = groups.findOne(group.getId());

		assertEquals(2, compare.getMembers().size());

		Set<User> items = compare.getMembers();
		assertTrue(items.contains(adam));
		assertTrue(items.contains(lebowski));

		System.out.println("*****");

		List<User> members = users.listUsersInGroup(compare.getId());
		assertEquals(2, members.size());
		assertTrue(members.contains(adam));
		assertTrue(members.contains(lebowski));
	}
}
