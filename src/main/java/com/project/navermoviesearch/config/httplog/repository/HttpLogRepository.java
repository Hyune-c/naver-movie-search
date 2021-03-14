package com.project.navermoviesearch.config.httplog.repository;

import com.project.navermoviesearch.config.httplog.entity.HttpLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HttpLogRepository extends JpaRepository<HttpLogEntity, Long> {

}
