package study.lms.mapper;

import org.apache.ibatis.annotations.Mapper;
import study.lms.domain.posts.PostsRequestDto;
import study.lms.domain.posts.PostsResponseDto;

import java.util.List;

@Mapper
public interface PostsMapper {

    List<PostsResponseDto>selectPostsList(PostsRequestDto requestDto);

    void insertPosts(PostsRequestDto requestDto);

    PostsResponseDto selectPosts(Long postsNum);
}
