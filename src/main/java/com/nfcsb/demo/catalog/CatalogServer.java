package com.nfcsb.demo.catalog;

import com.nfcsb.demo.context.ContextJSON;
import com.nfcsb.demo.context.RequestContextImpl;
import com.nfcsb.demo.service.SecurityCheck;
import com.nfcsb.demo.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;


@ComponentScan({"com.nfcsb.demo"})
@RestController
public class CatalogServer {

	private static final String template = "Hello, %s!";

	private final AtomicLong counter = new AtomicLong();

	private static final Logger logger = LoggerFactory.getLogger(CatalogServer.class);

	private final SessionService currentSession;

	@Autowired
	SecurityCheck securityCheck;

	@Autowired
	public CatalogServer(SessionService sessionService) {

		currentSession = sessionService;
	}

	@RequestMapping(value = "/greeting/{lname}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Greeting greeting(
		@RequestParam(value = "fname", defaultValue = "World") String fname,
		@PathVariable(value = "lname") String lname,
		RequestContextImpl context) {

		logger.info("request url = {}", context.getPath());

		if (lname.equals("twitter")) {
			logger.error("invalid lname = {}", lname);
			throw new BadRequestException("invalid lname: " + lname);
		}

		String msg = String.format(template, counter.getAndIncrement());
		return new Greeting(fname, lname, msg);
	}

	@RequestMapping(value = "/hello",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ContextJSON hello(RequestContextImpl context) {

		logger.info("Session: " + currentSession.get("Test"));
		return new ContextJSON(context);
	}

	@RequestMapping(value = "/throw/{exception}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String exceptionThrower(@PathVariable("exception") int exception) throws Throwable {

		switch (exception) {
			case 0:
				throw new IllegalArgumentException("Missing proper value!");

			case 1:
				throw new IllegalAccessException("Haleluja");

			case 2:
				throw new IOException("Bang!");

			default:
				throw new Throwable("The internet is broken!");
		}
	}

	@PreAuthorize("hasRole('USER')") // must have User role in order to access this REST!
	@RequestMapping(value = "/private",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String privateAccess() throws Throwable {

		return "OK";
	}

	@PreAuthorize("@securityCheck.hasToken(authentication)") // service check via method call
	@RequestMapping(value = "/private2",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String privateAccess2() throws Throwable {

		return "OK";
	}
}