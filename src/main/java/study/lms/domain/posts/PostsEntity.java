package study.lms.domain.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import study.lms.domain.BaseEntity;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@DynamicUpdate
@Table
@Entity(name = "POSTS")
public class PostsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postsNum;

    @Column(nullable = false, length = 50)
    private String postsTitle;
    @Column(nullable = false, length = 250)
    private String postsContent;
    @Column(nullable = false, length = 20)
    private String postsAuthor;
}
