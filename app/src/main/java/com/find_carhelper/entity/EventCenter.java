package com.find_carhelper.entity;

/**
 * EventBus 回调实体类
 *
 * @param <T>
 * @author kang gui yang
 */
public class EventCenter<T> {

    /**
     * 需要的数据
     */
    private T data;
    /**
     * 标识Code
     */
    private int eventCode = -1;

    public EventCenter(int eventCode) {
        this(eventCode, null);
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public T getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "EventCenter{" +
                "data=" + data +
                ", eventCode=" + eventCode +
                '}';
    }
}
