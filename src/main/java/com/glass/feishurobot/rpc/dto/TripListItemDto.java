package com.glass.feishurobot.rpc.dto;

import lombok.Data;

/**
 * 行程列表条目 DTO（对应 T+1 行程）
 */
@Data
public class TripListItemDto {
    /** 行程 id */
    private Long tripId;
    /** 行程开始时间（秒级时间戳） */
    private Long startTs;
    /** 行程结束时间（秒级时间戳） */
    private Long endTs;
    /** 行程持续时间（秒） */
    private Integer tripDuration;
    /** 行程总行驶里程（km） */
    private Double driveDistance;
    /** 智驾总行驶里程（km） */
    private Double adDistance;
    /** 行程类型：1=实时行程 2=T+1行程 */
    private Integer type;
    /** 起始位置 GPS 或地址 */
    private String startLocation;
    /** 终止位置 GPS 或地址 */
    private String endLocation;
}
