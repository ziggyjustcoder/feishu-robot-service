package com.glass.feishurobot.tools;

import java.util.List;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSON;
import com.glass.feishurobot.rpc.TripMcpInternalFeignService;
import com.glass.feishurobot.rpc.dto.IntelligentDriveDataDto;
import com.glass.feishurobot.rpc.dto.RealTimeTripDto;
import com.glass.feishurobot.rpc.dto.TripListItemDto;
import com.glass.feishurobot.rpc.dto.TripRecordEnabledTsResp;
import com.glass.feishurobot.rpc.dto.TripSettingChangeHistoryResp;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;

/**
 * 行程 MCP 工具服务
 * <p>
 * 提供 5 个 MCP Tool，供大模型在售后咨询场景中查询用户车辆行程相关信息。
 * 每个 @Tool 方法直接对应一个业务能力，通过 Feign 调用主模块内部接口获取数据。
 * </p>
 * <p>
 * 注意：此处使用的是 langchain4j 的 @Tool / @P 注解，
 * 以便被 dev.langchain4j.model.openai.OpenAiChatModel 直接识别和调度。
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TripMcpService {

    private final TripMcpInternalFeignService tripMcpInternalFeignService;

    @Tool(name = "queryTripRecordEnabledTime",
            value = "根据车辆 VIN 查询用户行程记录功能的开启时间。" +
                    "返回功能是否已开启（enabled）以及最近一次开启的时间戳（enabledTs，秒级）。" +
                    "适用场景：售后人员排查用户行程数据缺失时，确认用户是否已开启行程记录功能。")
    public String queryTripRecordEnabledTime(
            @P("车辆 VIN 码，例如：LSGKB52K9MA000001") String vin) {
        // TripRecordEnabledTsResp resp = tripMcpInternalFeignService.queryTripRecordEnabledTs(vin).getData();
        TripRecordEnabledTsResp resp = new TripRecordEnabledTsResp();
        resp.setVin(vin);
        resp.setEnabled(true);
        resp.setEnabledTs(1699999999L);
        return JSON.toJSONString(resp);
    }

    @Tool(name = "queryTripSettingChangeHistory",
            value = "根据车辆 VIN 查询用户行程记录功能开关的最近 5 条变更历史。" +
                    "每条记录包含变更的设置项（settingKey）、变更前的值（oldValue）和变更后的值（newValue）。" +
                    "适用场景：售后人员排查用户行程记录开关操作历史，了解用户是否曾经关闭过行程记录功能。")
    public String queryTripSettingChangeHistory(
            @P("车辆 VIN 码，例如：LSGKB52K9MA000001") String vin) {
        TripSettingChangeHistoryResp resp = tripMcpInternalFeignService.queryTripSettingChangeHistory(vin).getData();
        return JSON.toJSONString(resp);
    }

    @Tool(name = "queryDailyTripByDate",
            value = "根据车辆 VIN 和日期查询该日期的 T+1（离线处理完成）行程列表。" +
                    "每条行程记录包含行程 ID、开始/结束时间、行程时长、行驶里程、智驾里程等信息。" +
                    "注意：T+1 行程数据在当天次日才可用，若查询当天数据请使用实时行程查询工具。" +
                    "适用场景：售后人员查询用户某天的行程明细，协助分析驾驶数据异常。")
    public String queryDailyTripByDate(
            @P("车辆 VIN 码，例如：LSGKB52K9MA000001") String vin,
            @P("查询日期，格式 yyyy-MM-dd，例如：2024-01-15") String date) {
        List<TripListItemDto> trips = tripMcpInternalFeignService.queryDailyTrip(vin, date).getData();
        return JSON.toJSONString(trips);
    }

    @Tool(name = "queryTodayRealTimeTrip",
            value = "根据车辆 VIN 和用户 UID 查询车辆当天的实时行程列表。" +
                    "返回当天从零点至当前时刻的所有实时行程，每条包含行程时间、行驶里程、智驾里程等信息。" +
                    "适用场景：售后人员实时查看用户当天的驾驶情况，用于当天发生问题的排查。")
    public String queryTodayRealTimeTrip(
            @P("车辆 VIN 码，例如：LSGKB52K9MA000001") String vin,
            @P("用户 UID（数字），例如：12345678") Long uid) {
        List<RealTimeTripDto> trips = tripMcpInternalFeignService.queryTodayRealTimeTrip(vin, uid).getData();
        return JSON.toJSONString(trips);
    }

    @Tool(name = "queryIntelligentDriveData",
            value = "根据车辆 VIN 和日期查询该日期的智驾（辅助驾驶）统计数据。" +
                    "返回数据包括：智驾里程、智驾里程占比、成功变道次数、通过路口次数、" +
                    "0接管次数、AEB 触发次数、ACC/LCC/NGP 里程等智驾相关指标。" +
                    "适用场景：售后人员查询用户某天的智驾功能使用情况，协助分析智驾体验问题。")
    public String queryIntelligentDriveData(
            @P("车辆 VIN 码，例如：LSGKB52K9MA000001") String vin,
            @P("查询日期，格式 yyyy-MM-dd，例如：2024-01-15") String date) {
        IntelligentDriveDataDto data = tripMcpInternalFeignService.queryIntelligentDriveData(vin, date).getData();
        return JSON.toJSONString(data);
    }

}
