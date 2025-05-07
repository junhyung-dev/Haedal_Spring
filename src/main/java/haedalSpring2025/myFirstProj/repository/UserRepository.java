package haedalSpring2025.myFirstProj.repository;

import haedalSpring2025.myFirstProj.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository  //jpa가 코드랑 db매칭, 여기 interface에는 쓸기능만 구현
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username); //중복검사를 위한
    //세부구현은 jpa에서 되어있어서 선언만하고 사용하면됨
    Optional<User> findByUsername(String username);
}
