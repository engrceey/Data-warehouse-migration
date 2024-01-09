package com.progresssoft.clustereddatawarehouse.service.implementation;

import com.progresssoft.clustereddatawarehouse.entity.DealEntity;
import com.progresssoft.clustereddatawarehouse.repository.DealRepository;
import com.progresssoft.clustereddatawarehouse.utils.validationUtil.DealValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DealStoreServiceImplTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private DealValidationUtil dealValidationUtil;

    @InjectMocks
    private DealStoreServiceImpl dealStoreService;

    DealEntity dealEntity;

    @BeforeEach
    void setUp() {


        dealEntity = DealEntity.builder()
                .dealId("34556")
                .dealAmount(1000)
                .build();
    }

    @Test
    void GivenValidTableCSV_WhenUploadTableData_ReturnSuccess() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "Some Deal CSV data".getBytes(StandardCharsets.UTF_8)
        );

        List<DealEntity> res = new ArrayList<>();

        when(dealValidationUtil.extractUniqueDealRecordsToListByDealId(any(java.io.ByteArrayInputStream.class)))
                .thenReturn(res);
        when(dealValidationUtil.hasCSVFormat(file)).thenReturn(true);
        when(dealRepository.saveAll(res)).thenReturn(res);
        var actual = dealStoreService.uploadTableData(file);

        assertThat(actual).isNotNull();
        assertThat(actual.getResponseCode()).isEqualTo("00");
        assertThat(actual.getResponseMessage()).isEqualTo("Saved Fx Deals");
    }

}