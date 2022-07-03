package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.model.*;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:3001")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;
    @Autowired
    private final JwtService jwtService;
    private PostPostsRes postPostsRes;


    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService){
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }
    @ResponseBody
    @GetMapping("/list")
    public BaseResponse<List<GetPostListRes>> getReviewList(){
        List<GetPostListRes> getPostListRes = postService.reviewList();
        return new BaseResponse<>(getPostListRes);


    }

    @ResponseBody
    @GetMapping("/{postIdx}") //
    public BaseResponse<GetPostsRes> getPostsByIdx(@PathVariable("postIdx")int postIdx) {
        try{
            GetPostsRes getPostsRes = postProvider.retrievePosts(postIdx);
            return new BaseResponse<>(getPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostsRes> createPosts(@RequestBody PostPostsReq postPostsReq) {
        try{
            PostPostsRes  postPostsRes = postService.createPosts(postPostsReq.getUserIdx(), postPostsReq);
            return new BaseResponse<>(postPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @PatchMapping("/{postIdx}")
    public BaseResponse<String> modifyPost(@PathVariable ("postIdx") int postIdx, @RequestBody PatchPostsReq patchPostsReq){

    try {
        postService.modifyPost(patchPostsReq.getUserIdx(), postIdx, patchPostsReq);
        String result = "게시글 수정이 완료되었습니다.";
        return new BaseResponse<>(result);
    }
    catch (BaseException exception){
    return new BaseResponse<>((exception.getStatus()));
    }

    }
    @ResponseBody
    @PatchMapping("/{postIdx}/status")
    public BaseResponse<String> deletePost(@PathVariable ("postIdx") int postIdx) {
        try{
            postService.deletePost(postIdx);
            String result = "삭제를 성공했습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}