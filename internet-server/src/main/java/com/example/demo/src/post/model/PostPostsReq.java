package com.example.demo.src.post.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPostsReq {
    private int userIdx;
    private String title;
    private String content;
    //private String postImgUrls;
}
