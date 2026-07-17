package com.glass.feishurobot.rpc;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.glass.feishurobot.rpc.dto.IntelligentDriveDataDto;
import com.glass.feishurobot.rpc.dto.RealTimeTripDto;
import com.glass.feishurobot.rpc.dto.Result;
import com.glass.feishurobot.rpc.dto.TripListItemDto;
import com.glass.feishurobot.rpc.dto.TripRecordEnabledTsResp;
import com.glass.feishurobot.rpc.dto.TripSettingChangeHistoryResp;

/**
 * 行程报告内部接口 Feign 客户端
 * <p>
 * 对应 xp-vmp-trip-report-boot /internal/mcp/trip/* 的 5 个端点。
 * url 通过配置项 {@code trip-mcp.trip-report-base-url} 注入，
 * 详见 {@code application.yml} 及 {@link org.springframework.cloud.openfeign.FeignClient} 文档。
 * </p>
 */
@FeignClient(name = "XP-VMP-TRIP-REPORT-BOOT")
public interface TripMcpInternalFeignService {

    /**
     * 1. 查询行程记录功能开启时间
     */
    @GetMapping("/internal/mcp/trip/setting/enabledTs")
    Result<TripRecordEnabledTsResp> queryTripRecordEnabledTs(@RequestParam("vin") String vin);

    /**
     * 2. 查询最近 5 条行程开关变更记录
     */
    @GetMapping("/internal/mcp/trip/setting/changeHistory")
    Result<TripSettingChangeHistoryResp> queryTripSettingChangeHistory(@RequestParam("vin") String vin);

    /**
     * 3. 查询指定日期 T+1 行程列表
     *
     * @param date 格式 yyyy-MM-dd
     */
    @GetMapping("/internal/mcp/trip/daily")
    Result<List<TripListItemDto>> queryDailyTrip(@RequestParam("vin") String vin,
                                                 @RequestParam("date") String date);

    /**
     * 4. 查询当天实时行程
     */
    @GetMapping("/internal/mcp/trip/realtime/today")
    Result<List<RealTimeTripDto>> queryTodayRealTimeTrip(@RequestParam("vin") String vin,
                                                         @RequestParam("uid") Long uid);

    /**
     * 5. 查询指定日期智驾数据
     *
     * @param date 格式 yyyy-MM-dd
     */
    @GetMapping("/internal/mcp/trip/intelligentDrive")
    Result<IntelligentDriveDataDto> queryIntelligentDriveData(@RequestParam("vin") String vin,
                                                              @RequestParam("date") String date);
}
