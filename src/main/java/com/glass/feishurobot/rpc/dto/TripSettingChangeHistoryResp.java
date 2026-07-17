package com.glass.feishurobot.rpc.dto;

import java.util.List;
import lombok.Data;

/**
 * 行程开关变更历史响应
 */
@Data
public class TripSettingChangeHistoryResp {
    /** 车架号 */
    private String vin;
    /** 最近 N 条变更记录 */
    private List<TripSettingChangeRecordDto> records;
}
