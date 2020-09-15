package com.hriha.ShareRegistration.controller;


import com.hriha.ShareRegistration.domain.Share;
import com.hriha.ShareRegistration.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Share>> getAllShares() {
        List<Share> shares = shareService.getAll();

        if (shares.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shares, HttpStatus.OK);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Share> getShare(@PathVariable("id") Long shareId) {
        if (shareId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Share share = shareService.getById(shareId);

        if (share == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(share, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Share> saveShare(@RequestBody Share share) {
        HttpHeaders headers = new HttpHeaders();

        if (share == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        shareService.save(share);
        share.setCreationDate(LocalDateTime.now());

        return new ResponseEntity<>(share, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Share> update(@RequestBody Share share) {
        HttpHeaders headers = new HttpHeaders();

        if (share == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        shareService.save(share);

        return new ResponseEntity<>(share,headers,HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Share> delete(@PathVariable("id") Long id) {
        Share share = shareService.getById(id);

        if (share == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        shareService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
