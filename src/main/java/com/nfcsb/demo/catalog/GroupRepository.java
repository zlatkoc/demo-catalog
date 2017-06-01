package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.CatalogGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface GroupRepository extends JpaRepository<CatalogGroup, Long> {

}
