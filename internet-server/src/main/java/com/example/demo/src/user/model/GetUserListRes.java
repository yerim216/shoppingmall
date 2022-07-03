package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserListRes {
    private int userIdx;
    private String name;
    private String email;
    private String phone;

}