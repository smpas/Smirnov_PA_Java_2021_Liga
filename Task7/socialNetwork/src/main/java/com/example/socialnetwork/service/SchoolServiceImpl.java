package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.SchoolDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.School;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository repository;

    @Autowired
    public SchoolServiceImpl(SchoolRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SchoolDTO> getAllSchools() {
        List<School> schools = (List<School>) repository.findAll();
        List<SchoolDTO> schoolDTOS = new ArrayList<>();

        for (School school : schools) {
            schoolDTOS.add(convertSchoolToDTO(school));
        }
        return schoolDTOS;
    }

    @Override
    public SchoolDTO getSchoolById(Long id) {
        Optional<School> school = repository.findById(id);

        if (school.isPresent()) {
            return convertSchoolToDTO(school.get());
        } else {
            throw new EntityNotFoundException("School", id);
        }
    }

    @Override
    public SchoolDTO addNewSchool(SchoolDTO school) {
        return convertSchoolToDTO(repository.save(new School(school.getName(), school.getAddress())));
    }

    @Override
    public SchoolDTO updateSchool(SchoolDTO dto) {
        Optional<School> changingSchool = repository.findById(dto.getId());

        if (changingSchool.isPresent()) {
            School school = changingSchool.get();
            school.setName(dto.getName());
            school.setAddress(dto.getAddress());
            return convertSchoolToDTO(repository.save(school));
        } else {
            throw new EntityNotFoundException("School", dto.getId());
        }
    }

    @Override
    public void deleteSchool(Long id) {
        Optional<School> deletingSchool = repository.findById(id);

        if (deletingSchool.isPresent()) {
            repository.delete(deletingSchool.get());
        } else {
            throw new EntityNotFoundException("School", id);
        }
    }

    private SchoolDTO convertSchoolToDTO(School school) {
        List<Client> students = school.getClients();
        List<Long> studentsIDs = new ArrayList<>();

        for (Client student : students) {
            studentsIDs.add(student.getId());
        }
        return new SchoolDTO(school.getId(), school.getName(), school.getAddress(), studentsIDs);
    }
}
