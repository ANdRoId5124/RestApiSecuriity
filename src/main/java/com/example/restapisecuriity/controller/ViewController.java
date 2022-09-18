package com.example.restapisecuriity.controller;

import com.example.restapisecuriity.entity.Blog;
import com.example.restapisecuriity.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/view")
public class ViewController {
    private final BlogService blogService;

    public ViewController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("")
    public List<Blog> getBlogs(){
        return blogService.getAll();
    }
}
