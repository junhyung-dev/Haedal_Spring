package haedalSpring2025.myFirstProj.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(length = 2000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    //포스트하나당 여러개 좋아요 가능
    //@OneToMany(mappedBy = "post")
    //private List<Like> likes;

    public Post(User user, String content, String imageUrl) {
        this.user = user;
        this.imageUrl = imageUrl;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

}
