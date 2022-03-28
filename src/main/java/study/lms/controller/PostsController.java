package study.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.lms.domain.posts.PostsRequestDto;
import study.lms.domain.posts.PostsResponseDto;
import study.lms.service.PostsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostsController {

    private final PostsService service;

    @GetMapping("/list")
    public String selectList(PostsRequestDto requestDto, Model model) {
        List<PostsResponseDto> postsList = service.selectPostsList(requestDto);
        model.addAttribute("posts", postsList);
        return "posts/list";
    }

    @GetMapping("/write")
    public String writePage() {
        return "posts/write";
    }

    @PostMapping("/write")
    public String write(PostsRequestDto requestDto, Model model) {
        service.insertPosts(requestDto);
        if (0 > requestDto.getPostsNum()) {
            return "/error";
        } else {
            return "redirect:/posts/detail/" + requestDto.getPostsNum();
        }
    }

    @GetMapping("/detail/{postsNum}")
    public String detail(@PathVariable Long postsNum, Model model) {
        System.out.println(postsNum);
        PostsResponseDto posts = service.selectPosts(postsNum);
        model.addAttribute("posts", posts);
        return "posts/detail";
    }
}
