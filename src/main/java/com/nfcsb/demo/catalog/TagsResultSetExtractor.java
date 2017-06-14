package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.Tag;
import com.zandero.utils.extra.JsonUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class TagsResultSetExtractor implements ResultSetExtractor<List<Tag>> {

	@Override
	public List<Tag> extractData(ResultSet rs)
			throws SQLException, DataAccessException {

		if (rs != null && rs.next()) {
			String info = rs.getString("info");
			return JsonUtils.fromJsonAsList(info, Tag.class);
		}

		return Collections.emptyList();
	}
}
