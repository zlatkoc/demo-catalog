package com.nfcsb.demo.catalog.entities;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "catalog_user")
@ToString
//@NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE LOWER(u.name) = LOWER(?1)")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	@Getter
	private Long id;

	@Column(name = "name", nullable = false)
	@Getter
	private String name;

	public User() { }

	public User(String username) {
		name = username;
	}

	public User(long id, String username) {
		this.id = id;
		this.name = username;
	}
}
