package com.glass.feishurobot.rpc.dto;

import lombok.Data;

/**
 * 实时行程条目 DTO
 */
@Data
public class RealTimeTripDto {
    /** 行程 id */
    private Long tripId;
    /** 行程开始时间（毫秒时间戳） */
    private Long startTs;
    /** 行程结束时间（毫秒时间戳） */
    private Long endTs;
    /** 行程时长（秒） */
    private Integer tripDuration;
    /** 行驶里程（km） */
    private Double driveMileage;
    /** 智驾里程（km） */
    private Double adMileage;
    /** 起始位置 GPS */
    private String tripStartGps;
    /** 终止位置 GPS */
    private String tripEndGps;
    /** 智驾时长（秒） */
    private Double autoDriveDuration;
}
