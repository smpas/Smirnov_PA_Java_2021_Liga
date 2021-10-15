package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.SchoolDTO;
import com.example.socialnetwork.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/schools")
public class SchoolController {
    private SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public ResponseEntity<List<SchoolDTO>> getAllSchools() {
        return new ResponseEntity<>(schoolService.getAllSchools(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SchoolDTO> getSchool(@PathVariable Long id) {
        return new ResponseEntity<>(schoolService.getSchoolById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SchoolDTO> addNewSchool(@RequestBody SchoolDTO school) {
        return new ResponseEntity<>(schoolService.addNewSchool(school), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<SchoolDTO> updateSchool(@RequestBody SchoolDTO school) {
        return new ResponseEntity<>(schoolService.updateSchool(school), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
