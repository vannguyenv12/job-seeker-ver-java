package com.vannguyen.jobseeker.aspect;

import com.vannguyen.jobseeker.entity.User;
import com.vannguyen.jobseeker.exception.UnauthorizeException;
import com.vannguyen.jobseeker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CurrentUserAspect {

    private final UserRepository userRepository;

    @Before("@annotation(currentUser)")
    public void beforeMethodExecution(JoinPoint joinPoint, CurrentUser currentUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizeException();
        }

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UnauthorizeException());

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof User) {
                args[i] = user;
            }
        }
    }
}
