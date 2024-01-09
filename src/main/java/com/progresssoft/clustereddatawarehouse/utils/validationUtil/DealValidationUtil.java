package com.progresssoft.clustereddatawarehouse.utils.validationUtil;

import com.progresssoft.clustereddatawarehouse.entity.DealEntity;
import com.progresssoft.clustereddatawarehouse.exceptions.CustomException;
import com.progresssoft.clustereddatawarehouse.repository.DealRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.progresssoft.clustereddatawarehouse.DealTableHeaders.*;

@Log4j2
@Component
public class DealValidationUtil {

    public final String TYPE = "text/csv";

    private final DealRepository dealRepository;

    public DealValidationUtil(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType())
                || Objects.equals(file.getContentType(), "application/vnd.ms-excel");
    }

    public boolean doesDealRecordExist(String dealId) {
        return dealRepository.existsByDealId(dealId);
    }


    public List<DealEntity> extractDealRecordsToList(InputStream csvFile) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(csvFile, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<DealEntity> csvList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            DateTimeFormatter formatter = getDateTimeFormatter();
            for (CSVRecord csvRecord : csvRecords) {
                log.info("See csvRecord :: {}", csvRecord);
                log.info("See dealId :: {}", csvRecord.get(DEAL_ID.getName()));
                if (doesDealRecordExist(csvRecord.get(DEAL_ID.getName()))) {
                    log.info("See dealId :: {}", csvRecord.get(DEAL_ID.getName()));
                    continue;
                }
                DealEntity deals = DealEntity.builder()
                        .dealAmount(Double.parseDouble(csvRecord.get(DEAL_AMOUNT.getName())))
                        .fromCurrency(csvRecord.get(FROM_CURRENCY.getName()))
                        .toCurrency(csvRecord.get(TO_CURRENCY.getName()))
                        .dealId(csvRecord.get(DEAL_ID.getName()))
                        .dealTimestamp(LocalDateTime.parse(csvRecord.get(DEAL_TIMESTAMP.getName()), formatter))
                        .build();
                csvList.add(deals);
            }
            return csvList;
        } catch (IOException e) {
            log.error("Exception with message :: {}", e.getMessage());
            throw new CustomException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private static DateTimeFormatter getDateTimeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(""
                + "[M/dd/yyyy HH:mm:ss]"
                + "[MM/dd/yyyy HH:mm:ss]"
                + "[M/d/yyyy HH:mm:ss]"
                + "[MM/d/yyyy HH:mm:ss]"
        );
        return formatter;
    }

}
