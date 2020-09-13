package com.hriha.ShareRegistration.service;

import com.hriha.ShareRegistration.domain.Share;
import com.hriha.ShareRegistration.repo.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareService {

    @Autowired
    private ShareRepository shareRepository;

    public Share getById(Long id) {
        return shareRepository.getOne(id);
    }

    public Share save(Share share) {
        return shareRepository.save(share);
    }

    public void deleteById(Long id) {
        shareRepository.deleteById(id);
    }

    public void delete(Share share) {
        shareRepository.delete(share);
    }

    public List<Share> getAll() {
        return shareRepository.findAll();
    }

}
