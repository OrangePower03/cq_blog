package com.example.cloudAdminArticle.controller;

import com.example.cloudCommon.domain.dto.category.CategoryDto;
import com.example.cloudCommon.domain.dto.category.UpdateCategoryDto;
import com.example.cloudCommon.service.admin.CategoryService;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/{id}")
    public ResponseResult getOne(@PathVariable("id") Long id) {
        return categoryService.get(id);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/list")
    public ResponseResult list(int pageNum,int pageSize,String name,String status){
        return categoryService.listPage(pageNum,pageSize,name,status);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/export")
    public ResponseResult export() {
        return categoryService.export();
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PostMapping
    public ResponseResult add(@RequestBody CategoryDto categoryDto) {
        return categoryService.add(categoryDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PutMapping
    public ResponseResult updateOne(@RequestBody UpdateCategoryDto categoryDto) {
        return categoryService.updateOne(categoryDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return categoryService.delete(idList);
    }

}
