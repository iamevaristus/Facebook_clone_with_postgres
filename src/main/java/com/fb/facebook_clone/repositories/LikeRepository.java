package com.fb.facebook_clone.repositories;

import com.fb.facebook_clone.models.Likes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface LikeRepository extends CrudRepository<Likes, Long> {
}
