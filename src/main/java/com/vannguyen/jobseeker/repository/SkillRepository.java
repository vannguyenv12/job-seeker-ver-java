package com.vannguyen.jobseeker.repository;

import com.vannguyen.jobseeker.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findSkillByName(String name);

    boolean existsByName(String name);
}
