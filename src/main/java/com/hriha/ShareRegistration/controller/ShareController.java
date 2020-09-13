package com.hriha.ShareRegistration.controller;


import com.hriha.ShareRegistration.domain.Share;
import com.hriha.ShareRegistration.service.ShareService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/share")
public class ShareController {

    private final ShareService shareService;

    @Autowired
    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }

    @GetMapping
    public List<Share> list() {
        return shareService.getAll();
    }

    @GetMapping("{id}")
    public Share getOne(@PathVariable("id") Share share) {
        return share;
    }

    @PostMapping
    public Share create(@RequestBody Share share) {
        share.setCreationDate(LocalDateTime.now());
        return shareService.save(share);
    }

    @PutMapping("{id}")
    public Share update(
            @PathVariable("id") Share shareFromDb,
            @RequestBody Share share) {
        BeanUtils.copyProperties(share, shareFromDb, "id");

        return shareService.save(shareFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Share share) {
        shareService.delete(share);
    }



}
