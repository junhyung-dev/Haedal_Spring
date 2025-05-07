package haedalSpring2025.myFirstProj.repository;

import haedalSpring2025.myFirstProj.domain.Post;
import haedalSpring2025.myFirstProj.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Long countByUser(User user);
    List<Post> findByUser(User user);
}
