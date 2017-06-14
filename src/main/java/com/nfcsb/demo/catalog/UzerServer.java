package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.UserJSON;
import com.nfcsb.demo.catalog.entities.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@ComponentScan({"com.nfcsb.demo"})
@RestController
@RequestMapping(value = "/uzer",
                produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class UzerServer {

	private final UzerRepository users;

	@Autowired
	public UzerServer(UzerRepository repository) {

		users = repository; //new UzerRepository();
	}

	@RequestMapping(value = "/test",
	                method = RequestMethod.GET)
	public String testApi() {
		return "Hello there !";
	}

	@RequestMapping(value = "/fail",
	                method = RequestMethod.GET)
	public List<UserJSON> toFail()
	{
		final List<UserJSON> output = new ArrayList<>();

		final Iterable<Uzer> all = users.create();
		all.forEach(uzer -> output.add(new UserJSON(uzer))); // copy result to List

		return output;
	}


	@RequestMapping(method = RequestMethod.GET,
	                produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserJSON> listUsers() {

		final List<UserJSON> output = new ArrayList<>();

		final Iterable<Uzer> all = users.selectAll();
		all.forEach(uzer -> output.add(new UserJSON(uzer))); // copy result to List

		return output;
	}
}