package net.ijupiter.trading.api.settlement.services;

import net.ijupiter.trading.api.settlement.commands.StartSettlementCommand;
import net.ijupiter.trading.api.settlement.dto.SettlementDTO;
import net.ijupiter.trading.api.settlement.dto.SettlementReportDTO;
import net.ijupiter.trading.api.settlement.enums.SettlementStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * 结算服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface SettlementService {

    /**
     * 开始结算
     * 
     * @param command 开始结算命令
     * @return 结算ID
     */
    String startSettlement(StartSettlementCommand command);

    /**
     * 查询结算
     * 
     * @param settlementId 结算ID
     * @return 结算信息
     */
    SettlementDTO getSettlement(String settlementId);

    /**
     * 查询指定日期的结算
     * 
     * @param settlementDate 结算日期
     * @return 结算信息
     */
    SettlementDTO getSettlementByDate(LocalDate settlementDate);

    /**
     * 查询结算列表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 结算列表
     */
    List<SettlementDTO> getSettlementsByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * 查询指定状态的结算
     * 
     * @param status 结算状态
     * @return 结算列表
     */
    List<SettlementDTO> getSettlementsByStatus(SettlementStatus status);

    /**
     * 查询所有结算
     * 
     * @return 结算列表
     */
    List<SettlementDTO> getAllSettlements();

    /**
     * 检查结算是否存在
     * 
     * @param settlementId 结算ID
     * @return 是否存在
     */
    boolean settlementExists(String settlementId);

    /**
     * 检查指定日期是否已结算
     * 
     * @param settlementDate 结算日期
     * @return 是否已结算
     */
    boolean isSettled(LocalDate settlementDate);

    /**
     * 取消结算
     * 
     * @param settlementId 结算ID
     * @return 是否成功
     */
    boolean cancelSettlement(String settlementId);

    /**
     * 获取结算报告
     * 
     * @param settlementId 结算ID
     * @return 结算报告
     */
    SettlementReportDTO getSettlementReport(String settlementId);

    /**
     * 重新执行结算
     * 
     * @param settlementId 结算ID
     * @return 新的结算ID
     */
    String reExecuteSettlement(String settlementId);

    /**
     * 自动结算（系统定时任务调用）
     * 
     * @param settlementDate 结算日期
     * @return 结算ID
     */
    String autoSettlement(LocalDate settlementDate);
}