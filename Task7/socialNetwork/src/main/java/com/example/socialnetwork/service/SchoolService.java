package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.SchoolDTO;

import java.util.List;

public interface SchoolService {
    List<SchoolDTO> getAllSchools();

    SchoolDTO getSchoolById(Long id);

    SchoolDTO addNewSchool(SchoolDTO school);

    SchoolDTO updateSchool(SchoolDTO school);

    void deleteSchool(Long id);
}
