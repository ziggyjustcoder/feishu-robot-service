package com.glass.feishurobot.rpc.dto;

import lombok.Data;

/**
 * 智驾数据 DTO
 */
@Data
public class IntelligentDriveDataDto {
    /** 智驾里程（km） */
    private Double ngpMileage;
    /** 智驾里程占比 */
    private Double ngpMileageRatio;
    /** 成功变道次数 */
    private Integer laneChangeCount;
    /** 通过路口次数 */
    private Integer intersectionPassCount;
    /** 汇入汇出次数 */
    private Integer rampCount;
    /** 绕行次数 */
    private Integer detourCount;
    /** 环岛次数 */
    private Integer roundaboutCount;
    /** 掉头次数 */
    private Integer uTurnCount;
    /** 通过 ETC 次数 */
    private Integer etcCount;
    /** 园区里程（km） */
    private Double parkMileage;
    /** 小路里程（km） */
    private Double narrowMileage;
    /** 0接管次数 */
    private Integer zeroTakeOverCount;
    /** AEB 触发次数 */
    private Integer aebRaebCount;
    /** AES 触发次数 */
    private Integer aesCount;
    /** ACC 里程（km） */
    private Double accMileageTotal;
    /** LCC 里程（km） */
    private Double lccMileageTotal;
    /** NGP 里程（km） */
    private Double ngpMileageTotal;
    /** ADAS 里程（km） */
    private Double adasMileage;
    /** 主辅路切换次数 */
    private Integer mainAndSideRoadsCount;
    /** 三点掉头次数 */
    private Integer threePointTurnCount;
    /** 应对拥堵次数 */
    private Integer overCrowdCount;
    /** 帮你踩刹车和电门次数 */
    private Integer accAndDecCount;
    /** 园区闸机通行次数 */
    private Integer pointToPointGatePassCount;
    /** 行程时长（min） */
    private Double trajectoryDuration;
}
