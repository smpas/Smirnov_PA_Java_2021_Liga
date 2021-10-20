package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {
    Page<School> findAll(Pageable pageable);
}
