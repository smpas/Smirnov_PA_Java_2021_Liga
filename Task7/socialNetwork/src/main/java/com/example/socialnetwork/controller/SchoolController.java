package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.SchoolDTO;
import com.example.socialnetwork.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @GetMapping
    public Page<SchoolDTO> getAllSchools(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return schoolService.getAllSchools(pageable);
    }

    @GetMapping(value = "/{id}")
    public SchoolDTO getSchool(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    @PostMapping
    public SchoolDTO addNewSchool(@RequestBody SchoolDTO school) {
        return schoolService.addNewSchool(school);
    }

    @PutMapping
    public SchoolDTO updateSchool(@RequestBody SchoolDTO school) {
        return schoolService.updateSchool(school);
    }

    @DeleteMapping("/{id}")
    public void deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
    }
}
