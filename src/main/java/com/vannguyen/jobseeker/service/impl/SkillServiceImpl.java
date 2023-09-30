package com.vannguyen.jobseeker.service.impl;

import com.vannguyen.jobseeker.entity.Skill;
import com.vannguyen.jobseeker.entity.User;
import com.vannguyen.jobseeker.exception.NotFoundException;
import com.vannguyen.jobseeker.exception.UnauthorizeException;
import com.vannguyen.jobseeker.payload.SkillDto;
import com.vannguyen.jobseeker.repository.SkillRepository;
import com.vannguyen.jobseeker.repository.UserRepository;
import com.vannguyen.jobseeker.service.SkillService;
import com.vannguyen.jobseeker.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final ModelMapper modelMapper;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void createSkill(SkillDto skillDto) {

        User user = userService.getLoggedUser();

        skillDto.getNameList().forEach((skillName) -> {
            if (this.skillRepository.existsByName(skillName)) {
                Skill skill = this.skillRepository.findSkillByName(skillName).get();
                user.addSkill(skill);
                this.skillRepository.save(skill);
            } else {
                Skill skill = new Skill();
                skill.setName(skillName);
                user.addSkill(skill);
                this.skillRepository.save(skill);
            }

        });
    }

    public String assignSkill(SkillDto skillDto) {
        this.createSkill(skillDto);
        return "Assign Skill Successfully!";
    }

    @Override
    public List<SkillDto> findAllSkills() {
        List<Skill> skillList = this.skillRepository.findAll();


        List<SkillDto> skillDtoList = new ArrayList<>();

        skillList.forEach(skill -> {
            SkillDto skillDto = new SkillDto();
            Set<Long> userIds = skill.getUserList().stream().map(user -> user.getId()).collect(Collectors.toSet());
            skillDto.setUserId(userIds);
            skillDto.setName(skill.getName());
            skillDtoList.add(skillDto);
        });

        return skillDtoList;
    }

    @Override
    public SkillDto findSkillByName(String name) {
        Skill skill = this.skillRepository.findSkillByName(name).orElseThrow(() -> new NotFoundException("Skill", "name", name));
        return this.modelMapper.map(skill, SkillDto.class);
    }

    @Override
    public SkillDto findSkillById(long id) {
        Skill skill = this.skillRepository.findById(id).orElseThrow(() -> new NotFoundException("Skill", "id", "" + id));
        return this.modelMapper.map(skill, SkillDto.class);
    }

    @Override
    public SkillDto updateSkill(long id, SkillDto skillDto) {
        Skill skill = this.skillRepository.findById(id).orElseThrow(() -> new NotFoundException("Skill", "id", "" + id));

        skill.setName(skillDto.getName());

        Skill updatedSkill = this.skillRepository.save(skill);

        return this.modelMapper.map(updatedSkill, SkillDto.class);
    }

    @Transactional
    @Override
    public String deleteSkill(long id) {

        Skill skill = this.skillRepository.findById(id).orElseThrow(() -> new NotFoundException("Skill", "id", "" + id));

        this.userRepository.removeSkillFromUsers(userService.getLoggedUser().getId(), id);

        return "Delete Skill Successfully!";

    }
}
