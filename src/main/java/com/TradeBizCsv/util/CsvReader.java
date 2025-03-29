package com.TradeBizCsv.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CsvReader {
    public List<String[]> readCsv(MultipartFile file) {
        List<String[]> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            
            while ((line = reader.readNext()) != null) {
                data.add(line);
            }
        } 
        catch (IOException e) {
            log.error("error reading CSV={}", e.getMessage());
        } 
        catch (CsvValidationException e) {
            log.error("error reading CSV={}", e.getMessage());
        }

        return data;
    }
}
