package com.vannguyen.jobseeker.service;


import com.vannguyen.jobseeker.payload.SkillDto;

import java.util.List;
import java.util.Set;

public interface SkillService {
    void createSkill(SkillDto skillDto);
    String assignSkill(SkillDto skillDto);
    List<SkillDto> findAllSkills();
    SkillDto findSkillByName(String name);

    SkillDto findSkillById(long id);

    SkillDto updateSkill(long id, SkillDto skillDto);

    String deleteSkill(long id);
}
