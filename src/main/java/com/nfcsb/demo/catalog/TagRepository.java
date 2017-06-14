package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public class TagRepository implements TagDataRepository {

	private JdbcTemplate select;

	@Autowired
	public void setDataSource(JdbcTemplate template) {
		select = template;
	}

	@Override
	public List<Tag> getTags(long id) {
		return select.query("SELECT info FROM tags WHERE id = " + id, new TagsResultSetExtractor());
	}
}
