package haedalSpring2025.myFirstProj.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

//annotation - 프로그램이 알아듣는 주석

@Entity
@NoArgsConstructor //인자 필요없는 생성자를 만든다
@Getter //private 접근
@Setter
public class User {
    //DataBase table - entity 인식
    @Id //key에 붙임
    @GeneratedValue(strategy = GenerationType.AUTO) //ID가 유저 생성할때마다 자동으로 올라감
    private Long id; //primary key

    @Column(unique = true, nullable = false)
    private String username; //아이디

    @Column(unique = true, nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String bio; //자기소개

    @Column(name = "joined_at", nullable = false) //java에서는 joinedAt, db에서는 joined_at이므로 충돌나지않도록 이름을 맞춤
    private LocalDateTime joinedAt;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "user") //유저하나당 포스트 여러개
    private List<Post> posts;

//    @OneToMany(mappedBy = "user")
//    private List<Like> likes;
//
//    @OneToMany(mappedBy = "follower")
//    private List<Follow> followings;
//
//    @OneToMany(mappedBy = "following")
//    private List<Follow> followers;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.bio = null;
        this.joinedAt = LocalDateTime.now();
        this.imageUrl = null;
    }

}
