package com.example.cloudAdminLink.controller;

import com.example.cloudCommon.domain.dto.link.LinkDto;
import com.example.cloudCommon.service.admin.LinkService;
import com.example.cloudCommon.utils.ResponseResult;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/list")
    public ResponseResult getAll(int pageNum, int pageSize, String name, String status){
        return linkService.getAll(pageNum,pageSize, name, status);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") Long id) {
        return linkService.get(id);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PostMapping
    public ResponseResult add(@RequestBody LinkDto linkDto) {
        return linkService.add(linkDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PutMapping
    public ResponseResult updateOne(@RequestBody LinkDto linkDto) {
        return linkService.updateOne(linkDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return linkService.delete(idList);
    }

}
