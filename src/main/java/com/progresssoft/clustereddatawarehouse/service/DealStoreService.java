package com.progresssoft.clustereddatawarehouse.service;

import com.progresssoft.clustereddatawarehouse.dto.request.DealRecordRequestDto;
import com.progresssoft.clustereddatawarehouse.dto.response.ApiResponse;
import com.progresssoft.clustereddatawarehouse.entity.DealEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface DealStoreService {

    /**
     * service to store Table request
     * @param file Table(csv) data to persist
     * @return success message if completed
     */
    ApiResponse<String> uploadTableData(@RequestParam("file") MultipartFile file);

    /**
     * service to store json request
     * @param request json REST request
     * @return success message if completed
     */
    ApiResponse<String> storeApiRequestDeals(DealRecordRequestDto request);

    Page<DealEntity> findAllDeals(int page, int size);

}
