package com.nfcsb.demo.catalog;


import com.nfcsb.demo.catalog.entities.Uzer;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class UzerResultSetExtractor implements ResultSetExtractor {

	@Override
	public Object extractData(ResultSet rs) throws SQLException {
		Uzer person = new Uzer();
		person.setId(rs.getLong(1));
		person.setName(rs.getString(2));
		return person;
	}
}
