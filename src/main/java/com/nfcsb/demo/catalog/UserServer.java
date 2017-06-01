package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.User;
import com.nfcsb.demo.catalog.entities.UserJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@ComponentScan({"com.nfcsb.demo"}) @RestController
@RequestMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class UserServer {

	private final UserRepository users;

	@Autowired public UserServer(UserRepository userRepository) {

		users = userRepository;
	}

	@RequestMapping(value = "/test",
	                method = RequestMethod.GET)
	public String testApi() {
		return "Hello there !";
	}


	@RequestMapping(value = "/user",
	                method = RequestMethod.GET,
	                produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserJSON> listUsers() {

		final List<UserJSON> output = new ArrayList<>();

		final Iterable<User> all = users.findAll();
		all.forEach(user -> output.add(new UserJSON(user))); // copy result to List

		return output;
	}

	@RequestMapping(value = "/user/{username}",
	                method = RequestMethod.POST,
	                produces = MediaType.APPLICATION_JSON_VALUE)
	public UserJSON create(@PathVariable String username) {

		User created = users.save(new User(username));
		return new UserJSON(created);
	}
}
