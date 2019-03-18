package com.itheima.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AddPossword {
    private static String Add(String password){
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String encode = bc.encode(password);
        return encode;
    }

    public static void main(String[] args) {
        //$2a$10$Hozh.mZEObHQdv77ib650eN4JLqQBGigANmWomhwljVHGWdjTATvy
        //$2a$10$0uEZKoi..FITjv15ZGJ0Bu70s8.ppPCdCD.gJ37vdSmqnIa69cAkS
        String password = "123";
        String add = Add(password);
        System.out.println(add);
    }
}
