package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE LOWER(u.name) = LOWER(?1)")
	List<User> findByName(String name);

	/*@Query("SELECT u FROM User u JOIN CatalogGroup g WHERE g.id = ?1")
	List<User> listUsersInGroup(Long groupId);*/

	//select a from Author as a join a.Book as ab where ab.AuthorId like '%"hello"%';

	/*@Query("SELECT u FROM User u INNER JOIN Group m ON u.id = m.members_id WHERE m.group_id = ?1")*/
}
