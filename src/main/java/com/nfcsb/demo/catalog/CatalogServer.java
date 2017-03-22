package com.nfcsb.demo.catalog;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class CatalogServer {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting/{lname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Greeting greeting(
            @RequestParam(value="fname", defaultValue="World") String fname,
            @PathVariable(value="lname") String lname) {
        String msg = String.format(template, counter.getAndIncrement());
        return new Greeting(fname, lname, msg);
    }
}