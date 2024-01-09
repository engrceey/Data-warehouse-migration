package com.progresssoft.clustereddatawarehouse.service.implementation;

import com.progresssoft.clustereddatawarehouse.dto.request.DealRecordRequestDto;
import com.progresssoft.clustereddatawarehouse.dto.response.ApiResponse;
import com.progresssoft.clustereddatawarehouse.entity.DealEntity;
import com.progresssoft.clustereddatawarehouse.exceptions.CustomException;
import com.progresssoft.clustereddatawarehouse.repository.DealRepository;
import com.progresssoft.clustereddatawarehouse.service.DealStoreService;
import com.progresssoft.clustereddatawarehouse.utils.validationUtil.DealValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Service that manages Table(csv) / Rest Data migrations
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class DealStoreServiceImpl implements DealStoreService {

    private final DealRepository dealRepository;
    private final DealValidationUtil dealValidationUtil;


    /**
     * service to store Table request
     * @param file Table(csv) data to persist
     * @return ApiResponse<String>
     */
    @Override
    public ApiResponse<String> uploadTableData(MultipartFile file) {
        log.info("call to persist Table data for name:: {}", file.getOriginalFilename());
        if (!dealValidationUtil.hasCSVFormat(file)) {
            throw new CustomException("Select a valid csv file");
        }
        var records = dealValidationUtil.extractDealRecordsToList(fileToInputStream(file));
        dealRepository.saveAll(records);
        return ApiResponse.<String>builder()
                .responseMessage("Saved Fx Deals")
                .data("Deal Record uploaded successfully").responseCode("00").responseStatus(true).build();
    }

    InputStream fileToInputStream(MultipartFile file) {
        try{
            return file.getInputStream();
        } catch (Exception exception) {
            throw new CustomException("file error, check and try again");
        }

    }
    /**
     * service to store json request
     * @param request json REST request
     * @return ApiResponse<String>
     */

    @Override
    public ApiResponse<String> storeApiRequestDeals(DealRecordRequestDto request) {
        log.info("call to save api deals :: {} :: ", request.dealId());
        if (dealValidationUtil.doesDealRecordExist(request.dealId())) {
            return ApiResponse.<String>builder()
                    .responseMessage("Deal saved already")
                    .responseCode("00").responseStatus(true).build();
        }
        DealEntity dealEntity = new DealEntity();
        dealEntity.setDealAmount(request.dealAmount());
        dealEntity.setDealId(request.dealId());
        dealEntity.setDealTimestamp(request.dealTimestamp());
        dealEntity.setToCurrency(request.toCurrency());
        dealEntity.setFromCurrency(request.fromCurrency());

        dealRepository.save(dealEntity);
        return ApiResponse.<String>builder()
                .responseMessage("Deal saved successfully")
                .responseCode("00")
                .responseStatus(true)
                .build();
    }

}
