package com.TradeBizCsv.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.TradeBizCsv.common.constants;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CsvReader {
    
    public List<String[]> readCsv(MultipartFile file) {
        List<String[]> res = Collections.synchronizedList(new ArrayList<>());

        ExecutorService executorService = Executors.newFixedThreadPool(constants.THREAD_COUNT);

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                addFilterData(res, executorService, line);
            }
        } 
        catch (IOException | CsvValidationException e ) {
            log.error("error reading CSV={}", e.getMessage());
            e.printStackTrace();
        } 
        finally {
            executorService.shutdown();

            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                log.error("Executor service interrupted={}", e.getMessage());
            }
        }

        return res;
    }

    private void addFilterData(List<String[]> res, ExecutorService executorService, String[] line) {
        String[] currentLine = line;
        executorService.submit(() -> {
            if (constants.Bubin.equals(currentLine[4])) {
                res.add(currentLine);    
            }
        });
    }
}
