package com.example.demo.src.post.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class GetPostListRes {
    private int postIdx;
    private int userIdx;
    private String title;
    private String content;
    private String createdAt;
}
