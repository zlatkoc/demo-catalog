package com.nfcsb.demo.catalog;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class UzerRowExtractor implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		UzerResultSetExtractor extractor = new UzerResultSetExtractor();
		return extractor.extractData(rs);
	}
}
