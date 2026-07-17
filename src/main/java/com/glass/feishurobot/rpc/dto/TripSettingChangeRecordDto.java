package com.glass.feishurobot.rpc.dto;

import lombok.Data;

/**
 * 行程开关变更记录条目
 */
@Data
public class TripSettingChangeRecordDto {
    /** 车架号 */
    private String vin;
    /** 用户 uid */
    private Long uid;
    /** 设置项 key（module_enabled / display_address） */
    private String settingKey;
    /** 变更前值 */
    private String oldValue;
    /** 变更后值 */
    private String newValue;
}
