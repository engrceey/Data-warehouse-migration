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
import java.util.HashMap;
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


    public List<DealEntity> extractUniqueDealRecordsToListByDealId(InputStream csvFile) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(csvFile, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            DateTimeFormatter formatter = getDateTimeFormatter();

            /**
             * Using a map to store dealId, and it's record to manage duplicates
             */
            HashMap<String, DealEntity> dealMap = new HashMap<>();
            for (CSVRecord csvRecord : csvRecords) {

                final String dealId = csvRecord.get(DEAL_ID.getName());
                final double dealAmount = Double.parseDouble(csvRecord.get(DEAL_AMOUNT.getName()));
                final String fromCurrency = csvRecord.get(FROM_CURRENCY.getName());
                final String toCurrency = csvRecord.get(TO_CURRENCY.getName());
                final LocalDateTime dealTimeStamp = LocalDateTime.parse(csvRecord.get(DEAL_TIMESTAMP.getName()), formatter);

                if (dealMap.containsKey(dealId)) {
                    continue;
                }
                DealEntity deals = DealEntity.builder()
                        .dealAmount(dealAmount)
                        .fromCurrency(fromCurrency)
                        .toCurrency(toCurrency)
                        .dealId(dealId)
                        .dealTimestamp(dealTimeStamp)
                        .build();
                dealMap.put(dealId, deals);
            }
            return dealMap.values().stream().toList();
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
