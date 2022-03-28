package study.lms.domain.posts;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostsRequestDto {

    private Long postsNum;

    @NotEmpty(message = "제목은 필수값입니다.")
    private String postsTitle;

    @NotEmpty(message = "내용은 필수값입니다.")
    private String postsContent;

    @NotEmpty(message = "작성자는 필수값입니다.")
    private String postsAuthor;
}
