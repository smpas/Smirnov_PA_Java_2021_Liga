package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.SchoolDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolService {
    Page<SchoolDTO> getAllSchools(Pageable pageable);

    SchoolDTO getSchoolById(Long id);

    SchoolDTO addNewSchool(SchoolDTO school);

    SchoolDTO updateSchool(SchoolDTO school);

    void deleteSchool(Long id);
}
