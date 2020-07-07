package com.asing1elife.teamnote.model.simple;

public class PageParam {

    public enum ConditionType {
        equal, like, time
    }

    // 参数名称
    private String name;

    // 参数值
    private String value;

    // 参数是否为对象
    private Boolean object = false;

    // 条件类型
    private ConditionType type = ConditionType.equal;

    // 开始时间
    private String startTime;

    // 结束时间
    private String endTime;

    public PageParam() {
    }

    public PageParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getObject() {
        return object;
    }

    public void setObject(Boolean object) {
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ConditionType getType() {
        return type;
    }

    public void setType(ConditionType type) {
        this.type = type;
    }
}
