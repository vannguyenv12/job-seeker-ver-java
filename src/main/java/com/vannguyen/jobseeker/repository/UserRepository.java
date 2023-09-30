package com.vannguyen.jobseeker.repository;

import com.vannguyen.jobseeker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "DELETE FROM users_skills\n" +
            "WHERE user_id = :userId AND skill_id = :skillId", nativeQuery = true)
    void removeSkillFromUsers(long userId, long skillId);

}
