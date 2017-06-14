package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.Tag;
import com.zandero.utils.extra.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

/**
 *
 */

@ComponentScan({"com.nfcsb.demo"})
@RestController
@RequestMapping(value = "/tags",
                produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class TagsServer {

	private final TagDataRepository tags;

	@Autowired
	public TagsServer(TagDataRepository repository) {

		tags = repository; //new UzerRepository();
	}

	@RequestMapping(value = "{tag}",
	                method = RequestMethod.GET,
	                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getById(@PathVariable("tag") long id) {

		List<Tag> list = tags.getTags(id);
		return JsonUtils.toJson(list);
	}
}
