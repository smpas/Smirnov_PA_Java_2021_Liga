package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.School;

import java.util.List;

public interface SchoolService {
    List<School> getAllSchools();

    School getSchoolById(Long id);

    School addNewSchool(School school);

    School updateSchool(School school);

    School deleteSchool(Long id);
}
