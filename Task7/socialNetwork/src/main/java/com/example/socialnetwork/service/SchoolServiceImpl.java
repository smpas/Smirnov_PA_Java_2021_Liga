package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.SchoolDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.School;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository repository;

    @Override
    public Page<SchoolDTO> getAllSchools(Pageable pageable) {
        Page<School> schools = repository.findAll(pageable);
        Page<SchoolDTO> schoolDTOs = schools.map(new Function<School, SchoolDTO>() {
            @Override
            public SchoolDTO apply(School school) {
                return convertSchoolToDTO(school);
            }
        });

        return schoolDTOs;
    }

    @Override
    public SchoolDTO getSchoolById(Long id) {
        return repository.findById(id)
                .map(this::convertSchoolToDTO)
                .orElseThrow(() -> new EntityNotFoundException(School.class.getName(), id));
    }

    @Override
    @Transactional
    public SchoolDTO addNewSchool(SchoolDTO school) {
        return convertSchoolToDTO(repository.save(new School(school.getName(), school.getAddress())));
    }

    @Override
    @Transactional
    public SchoolDTO updateSchool(SchoolDTO dto) {
        School changingSchool = repository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException(School.class.getName(), dto.getId()));

        changingSchool.setName(dto.getName());
        changingSchool.setAddress(dto.getAddress());
        return convertSchoolToDTO(repository.save(changingSchool));
    }

    @Override
    @Transactional
    public void deleteSchool(Long id) {
        School deletingSchool = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(School.class.getName(), id));

        repository.delete(deletingSchool);
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
