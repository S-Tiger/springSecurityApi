package study.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.lms.domain.posts.PostsEntity;

public interface PostsRepository extends JpaRepository<PostsEntity, Long> {
}
