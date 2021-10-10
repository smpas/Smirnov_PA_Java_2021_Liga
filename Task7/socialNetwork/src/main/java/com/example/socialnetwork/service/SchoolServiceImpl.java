package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.School;
import com.example.socialnetwork.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private SchoolRepository repository;

    @Override
    public List<School> getAllSchools() {
        return (List<School>) repository.findAll();
    }

    @Override
    public School getSchoolById(Long id) {
        Optional<School> school = repository.findById(id);
        return school.orElse(null);
    }

    @Override
    public School addNewSchool(School school) {
        return repository.save(school);
    }

    @Override
    public School updateSchool(School school) {
        Optional<School> changingSchool = repository.findById(school.getId());

        if (changingSchool.isPresent()) {
            return repository.save(school);
        } else {
            return null;
        }
    }

    @Override
    public School deleteSchool(Long id) {
        Optional<School> deletingSchool = repository.findById(id);

        if (deletingSchool.isPresent()) {
            repository.delete(deletingSchool.get());
            return deletingSchool.get();
        } else {
            return null;
        }
    }
}
