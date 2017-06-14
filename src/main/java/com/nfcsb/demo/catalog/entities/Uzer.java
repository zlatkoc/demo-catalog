package com.nfcsb.demo.catalog.entities;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
public class Uzer {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String name;

	public Uzer() {
	}

	public Uzer(String username) {
		name = username;
	}

	public Uzer(long id, String username) {
		this.id = id;
		this.name = username;
	}
}
