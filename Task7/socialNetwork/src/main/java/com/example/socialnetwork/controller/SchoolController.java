package com.example.socialnetwork.controller;

import com.example.socialnetwork.entity.School;
import com.example.socialnetwork.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/schools")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> allSchools = schoolService.getAllSchools();
        return new ResponseEntity<>(allSchools, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<School> getSchool(@PathVariable Long id) {
        School school = schoolService.getSchoolById(id);

        if (school != null) {
            return new ResponseEntity<>(school, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<School> addNewSchool(@RequestBody School school) {
        School createdSchool = schoolService.addNewSchool(school);
        return new ResponseEntity<>(createdSchool, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<School> updateSchool(@RequestBody School school) {
        School changedSchool = schoolService.updateSchool(school);

        if (changedSchool != null) {
            return new ResponseEntity<>(changedSchool, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchool(@PathVariable Long id) {
        School deletedSchool = schoolService.deleteSchool(id);

        if (deletedSchool != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
