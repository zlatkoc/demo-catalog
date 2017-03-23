package com.nfcsb.demo.catalog;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import com.nfcsb.demo.context.ContextJSON;
import com.nfcsb.demo.context.RequestContext;
import com.nfcsb.demo.context.RequestContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class CatalogServer {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final Logger logger = LoggerFactory.getLogger(CatalogServer.class);

    @RequestMapping(value = "/greeting/{lname}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Greeting greeting(
            @RequestParam(value="fname", defaultValue="World") String fname,
            @PathVariable(value="lname") String lname,
            @Autowired RequestContextImpl context)
    {
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
    public ContextJSON hello(@Autowired RequestContextImpl context) {

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
}