package com.hriha.ShareRegistration.repo;

import com.hriha.ShareRegistration.domain.Share;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Share, Long> {
}
