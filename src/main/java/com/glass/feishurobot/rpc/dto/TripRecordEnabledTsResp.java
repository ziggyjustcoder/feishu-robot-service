package com.glass.feishurobot.rpc.dto;

import lombok.Data;

/**
 * 行程记录功能开启时间响应 DTO
 */
@Data
public class TripRecordEnabledTsResp {
    /** 车架号 */
    private String vin;
    /**
     * 行程记录功能最近一次开启时间（秒级时间戳）；
     * 若用户从未开启则为 null
     */
    private Long enabledTs;
    /** 功能是否已开启 */
    private Boolean enabled;
}
