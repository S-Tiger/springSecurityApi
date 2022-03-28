package study.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.lms.domain.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity> findByUserEmail(String userEmail);
}
