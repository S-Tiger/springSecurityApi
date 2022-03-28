package study.lms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.lms.domain.posts.PostsRequestDto;
import study.lms.domain.posts.PostsResponseDto;
import study.lms.mapper.PostsMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsMapper mapper;

    @Transactional(readOnly = true)
    public List<PostsResponseDto> selectPostsList(PostsRequestDto requestDto){
        return mapper.selectPostsList(requestDto);
    }

    @Transactional
    public void insertPosts(PostsRequestDto requestDto) {
        mapper.insertPosts(requestDto);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto selectPosts(Long postsNum) {
        return mapper.selectPosts(postsNum);
    }
}
