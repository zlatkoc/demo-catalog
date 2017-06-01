package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE LOWER(u.name) = LOWER(?1)")
	List<User> findByName(String name);

	//select u from catalog_user u where exists (select 1 from catalog_group_members gm where gm.catalog_group_id = 1 And gm.members_id = u.id);
	//@Query("select u FROM User u , CatalogGroup g WHERE g.id=:groupId")
	@Query("select u FROM User u WHERE EXISTS (SELECT 1 FROM CatalogGroup g WHERE g.id=:groupId AND u.id in elements(g.members))")
	List<User> listUsersInGroup(@Param("groupId" ) Long groupId);
}
