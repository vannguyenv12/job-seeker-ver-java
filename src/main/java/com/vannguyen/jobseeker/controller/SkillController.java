package com.vannguyen.jobseeker.controller;

import com.vannguyen.jobseeker.payload.SkillDto;
import com.vannguyen.jobseeker.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/skills")
public class SkillController {
    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<String> assignSkill(@RequestBody SkillDto skillDto) {
        return new ResponseEntity<>(this.skillService.assignSkill(skillDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SkillDto>> findAllSkills() {
        return new ResponseEntity<>(this.skillService.findAllSkills(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(this.skillService.deleteSkill(id), HttpStatus.OK);
    }
}
