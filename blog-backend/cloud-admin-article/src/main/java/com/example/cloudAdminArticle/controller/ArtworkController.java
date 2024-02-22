package com.example.cloudAdminArticle.controller;

import com.example.cloudCommon.domain.dto.article.AddArticleDto;
import com.example.cloudCommon.domain.dto.article.UpdateArticleDto;
import com.example.cloudCommon.service.admin.ArticleService;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/article")
public class ArtworkController {
    @Autowired
    private ArticleService articleService;

    @PreAuthorize("@ppp.hasRole('admin')")
    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto addArticleDto) {
        return articleService.add(addArticleDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/list")
    public ResponseResult getAll(int  pageNum, int pageSize,String title,String summary) {
        return articleService.getAll(pageNum,pageSize,title,summary);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/{id}")
    public ResponseResult getOneArticle(@PathVariable("id") Long id) {
        return articleService.getOneArticle(id);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PutMapping
    public ResponseResult updateArticle(@RequestBody UpdateArticleDto updateArticleDto) {
        return articleService.updateArticle(updateArticleDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") String  ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return articleService.deleteArticle(idList);
    }
}
