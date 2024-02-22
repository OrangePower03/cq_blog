package com.example.cloudAdminArticle.controller;

import com.example.cloudCommon.domain.dto.tag.TagDto;
import com.example.cloudCommon.service.admin.TagService;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String name, String remark) {
        return tagService.pageTagList(pageNum,pageSize,name,remark);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PostMapping
    public ResponseResult addTag(@RequestBody TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @DeleteMapping("/{ids}")
    public ResponseResult deleteTag(@PathVariable("ids") String ids) {
        return tagService.deleteTag(ids);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable("id") Integer id) {
        return tagService.getTag(id);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDto tagDto) {
        return tagService.updateTag(tagDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }
}
