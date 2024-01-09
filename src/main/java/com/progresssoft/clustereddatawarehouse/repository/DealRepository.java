package com.progresssoft.clustereddatawarehouse.repository;


import com.progresssoft.clustereddatawarehouse.entity.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<DealEntity, Long> {
    boolean existsByDealId(String dealID);

}
