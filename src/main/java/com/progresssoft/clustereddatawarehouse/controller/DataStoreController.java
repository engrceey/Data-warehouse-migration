package com.progresssoft.clustereddatawarehouse.controller;


import com.progresssoft.clustereddatawarehouse.dto.request.DealRecordRequestDto;
import com.progresssoft.clustereddatawarehouse.dto.response.ApiResponse;
import com.progresssoft.clustereddatawarehouse.service.DealStoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller to manage Table (csv) & REST migration requests
 */

@Log4j2
@RestController
@RequestMapping("/v1/datastore")
@RequiredArgsConstructor
public class DataStoreController {

    private final DealStoreService dealStoreService;

    /**
     * endpoint to store Table request
     * @param file Table(csv) data
     * @return ApiResponse success object
     */

    @Tag(
            name = "Persist Table Deal record",
            description = "this Api Persist Table Deal record")
    @PostMapping(path = "/persist-table-data",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<?>> uploadTableData(@RequestParam("file") MultipartFile file) {
        log.info("request to persist Deal Table record :: {} ::", file.getOriginalFilename());
        return ResponseEntity.ok(dealStoreService.uploadTableData(file));
    }

    /**
     * endpoint to store Table request
     * @param requestDto receives Rest Json Deal Data
     * @return ApiResponse success object
     */

    @Tag(
            name = "Persist Json Deal record",
            description = "this Api Persist Json Deal record")
    @PostMapping(path = "/persist-json-data")
    public ResponseEntity<ApiResponse<?>> migrateDataFromRest(@Valid @RequestBody DealRecordRequestDto requestDto) {
        log.info("request to save Deal from API :: {} ::", requestDto.dealId());
        return ResponseEntity.ok(dealStoreService.storeApiRequestDeals(requestDto));
    }

}
