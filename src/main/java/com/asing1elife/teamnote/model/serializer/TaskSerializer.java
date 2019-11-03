package com.asing1elife.teamnote.model.serializer;

import com.asing1elife.teamnote.model.TaskModel;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.IOException;
import java.util.List;

public class TaskSerializer extends JsonSerializer<List<TaskModel>> {

    @Override
    public void serialize(List<TaskModel> tasks, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<SimpleTask> simpleTasks = Lists.newArrayList();

        tasks.forEach(task -> simpleTasks.add(new SimpleTask(task.getId())));

        jsonGenerator.writeObject(simpleTasks);
    }

    @Data
    class SimpleTask {
        private Long id;

        public SimpleTask(Long id) {
            this.id = id;
        }
    }
}
