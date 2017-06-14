package com.nfcsb.demo.catalog.entities;

/**
 * For JSON serialization purposes only
 */
public class UserJSON {

	public long id;

	public String username;

	private UserJSON() {} // for Jackson

	public UserJSON(User user) {

		id = user.getId();
		username = user.getName();
	}

	public UserJSON(Uzer user) {

		id = user.getId();
		username = user.getName();
	}
}
