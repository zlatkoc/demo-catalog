package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public interface TagDataRepository {
	List<Tag> getTags(long id);
}
