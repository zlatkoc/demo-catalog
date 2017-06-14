package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class TagRowExtractor implements RowMapper<List<Tag>> {

	@Override
	public List<Tag> mapRow(ResultSet rs, int rowNum) throws SQLException {

		TagsResultSetExtractor extractor = new TagsResultSetExtractor();
		return extractor.extractData(rs);
	}
}
