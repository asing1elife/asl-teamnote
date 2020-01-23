package com.asing1elife.teamnote.model;

import com.asing1elife.teamnote.model.dictionary.TaskLevel;
import com.asing1elife.teamnote.model.dictionary.TaskStatus;
import com.asing1elife.teamnote.model.serializer.DailyRecordSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "al_task")
public class TaskModel extends BaseModel {

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    private TaskTagModel taskTag;

    @ManyToOne
    private ProjectModel project;

    @ManyToOne
    private TaskLevel level = TaskLevel.TALE_Normal;

    @ManyToOne
    private TaskStatus status = TaskStatus.TAST_Init;

    @Column
    private Timestamp planBeginDate;

    @Column
    private Timestamp planFinishDate;

    @Column
    private Timestamp beginDate;

    @Column
    private Timestamp finishDate;

    @ManyToMany
    // 使用自定义的序列化方式简化 DailyRecordModel ，防止双向关联的无限嵌套
    @JsonSerialize(using = DailyRecordSerializer.class)
    @JoinTable(name = "al_daily_record_task", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "dailyRecord_id", referencedColumnName = "id"))
    private List<DailyRecordModel> records = Lists.newArrayList();

    /**
     * 因为 @Data 自动重写的 toString() 中会包含 DailyRecord
     * 在删除 Task 与 DailyRecord 时会抛出无限嵌套的异常
     * 所以手动将 toString() 重写，并移除 DailyRecord
     */
    @Override
    public String toString() {
        return "TaskModel{" +
          "name='" + name + '\'' +
          ", description='" + description + '\'' +
          ", taskTag=" + taskTag +
          ", project=" + project +
          ", level=" + level +
          ", status=" + status +
          ", planBeginDate=" + planBeginDate +
          ", planFinishDate=" + planFinishDate +
          ", beginDate=" + beginDate +
          ", finishDate=" + finishDate +
          '}';
    }
}
