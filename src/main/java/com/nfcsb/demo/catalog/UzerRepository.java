package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Component
public class UzerRepository implements UzerDataRepo {

	private JdbcTemplate select;

	@Autowired
	public void setDataSource(JdbcTemplate template) {
		select = template;
	}

	public List<Uzer> selectAll() {
		//JdbcTemplate select = new JdbcTemplate(dataSource);

		return select.query("select id, name from catalog_user", new UzerRowExtractor());
	}

	@Transactional
	public List<Uzer> create() {

	//JdbcTemplate select = new JdbcTemplate(dataSource);

		select.execute("insert into catalog_user (id, name) values (1000, 'bla')");
		select.execute("insert into catalog_user (id, name) values (1000, 'bla')");

		return selectAll();
	}
}
