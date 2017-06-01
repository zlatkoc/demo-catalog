package com.nfcsb.demo.catalog.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "catalog_group")
@EqualsAndHashCode
public class CatalogGroup {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",
	        nullable = false)
	private Long id;

	@Getter
	@Column(name = "name",
	        nullable = false)
	private String groupName;

	@Getter
	@OneToMany(fetch = FetchType.EAGER) // for lazy loading we need session management
	private Set<User> members = new HashSet<>();

	private CatalogGroup() {

	}

	public CatalogGroup(String name) {
		groupName = name;
	}

	public void addMember(User user) {
		members.add(user);
	}
}
