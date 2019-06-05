package com.asing1elife.teamnote.model;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "al_daily_record")
public class DailyRecordModel extends BaseModel {

    // 所属日志
    @ManyToOne
    private DailyModel daily;

    // 日期
    @Column
    private Integer day;

    // 是否加班
    @Column
    private boolean isExtra = false;

    // 是否还班
    @Column
    private boolean isRepay = false;

    // 是否休息
    @Column
    private boolean isRest = false;

    @ManyToMany
    @JoinTable(name = "al_daily_record_task", joinColumns = @JoinColumn(name = "dailyRecord_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private List<TaskModel> tasks = Lists.newArrayList();

}
