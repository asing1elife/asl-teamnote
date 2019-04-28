package com.asing1elife.teamnote.model.serializer;

import com.asing1elife.teamnote.model.DailyRecordModel;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.IOException;
import java.util.List;

public class DailyRecordSerializer extends JsonSerializer<List<DailyRecordModel>> {

    @Override
    public void serialize(List<DailyRecordModel> records, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<SimpleDailyRecord> simpleRecords = Lists.newArrayList();

        records.forEach(record -> simpleRecords.add(new SimpleDailyRecord(record.getId())));

        jsonGenerator.writeObject(simpleRecords);
    }

    @Data
    class SimpleDailyRecord {
        private Long id;

        public SimpleDailyRecord(Long id) {
            this.id = id;
        }
    }
}
