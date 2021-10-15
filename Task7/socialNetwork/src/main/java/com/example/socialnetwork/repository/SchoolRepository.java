package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.School;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {
}
