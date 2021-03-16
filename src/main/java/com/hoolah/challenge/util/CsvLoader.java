package com.hoolah.challenge.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.util.Collections;
import java.util.List;

public final class CsvLoader {

    public static <T> List<T> loadCsv(Class<T> type, String fileName) {
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        return loadCsv(type, fileName, bootstrapSchema);
    }

    public static <T> List<T> loadCsv(Class<T> type, String fileName, CsvSchema schema) {
        try {
            CsvMapper mapper = new CsvMapper();
            ClassLoader classLoader = CsvLoader.class.getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            MappingIterator<T> readValues = mapper.readerFor(type).with(schema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
