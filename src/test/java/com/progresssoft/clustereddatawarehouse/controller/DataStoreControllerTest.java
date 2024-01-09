package com.progresssoft.clustereddatawarehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progresssoft.clustereddatawarehouse.dto.response.ApiResponse;
import com.progresssoft.clustereddatawarehouse.entity.DealEntity;
import com.progresssoft.clustereddatawarehouse.service.DealStoreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = DataStoreController.class)
@ExtendWith(MockitoExtension.class)
class DataStoreControllerTest {

    @MockBean
    private DealStoreService dealStoreService;

    @Autowired
    private MockMvc mockMvc;

    ApiResponse<String> response;

    @BeforeEach
    void setUp() {
        response = ApiResponse.<String>builder()
                .responseStatus(true)
                .responseMessage("Saved Fx Deals")
                .responseCode("00")
                .build();
    }

    @Test
    void GivenValidDealRecord_WhenUploadTableData_ThenReturnSuccess() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "Some Deal CSV data".getBytes(StandardCharsets.UTF_8)
        );

        when(dealStoreService.uploadTableData(file)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/datastore/persist-table-data")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(dealStoreService, times(1)).uploadTableData(file);

    }

    @Test
    void GivenPageSize_WhenFetchDeals_ReturnSuccess() throws Exception {
        int page = 0;
        int size = 10;
        Page<DealEntity> mockPage = mock(Page.class);
        when(dealStoreService.findAllDeals(page, size)).thenReturn(mockPage);


        MvcResult result = mockMvc.perform(get("/v1/datastore/fetch-deal-records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int actual = result.getResponse().getStatus();
        Assertions.assertEquals(200, actual);
        verify(dealStoreService, times(1)).findAllDeals(page, size);

    }


}