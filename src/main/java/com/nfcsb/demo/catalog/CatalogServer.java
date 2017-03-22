package com.nfcsb.demo.catalog;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            HttpServletRequest request)
    {
        logger.info("request url = {}", request.getRequestURI());

        if (lname.equals("twitter")) {
            logger.error("invalid lname = {}", lname);
            throw new BadRequestException("invalid lname: " + lname);
        }

        String msg = String.format(template, counter.getAndIncrement());
        return new Greeting(fname, lname, msg);
    }
}