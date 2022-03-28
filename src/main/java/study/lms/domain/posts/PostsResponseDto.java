package study.lms.domain.posts;

import lombok.Data;

@Data
public class PostsResponseDto{

    private Long postsNum;
    private String postsTitle;
    private String postsContent;
    private String postsAuthor;
    private String createDate;
    private String modifiedDate;
}
