package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.Uzer;

import java.util.List;

/**
 *
 */
public interface UzerDataRepo {

	//void setDataSource(DataSource ds);

	/*void create(String firstName, String lastName);

	List<Uzer> select(String firstname, String lastname);
*/
	List<Uzer> selectAll();

	/*void deleteAll();

	void delete(String firstName, String lastName);*/
}
